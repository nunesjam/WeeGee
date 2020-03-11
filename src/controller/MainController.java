package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import animatefx.animation.ZoomIn;
import application.server.ServerMain;
import application.chat.*;
import controller.chat.ChatController;
import controller.login.LoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

	private ServerMain server;
	private String userName;
	private String ip;
	private int port;
	private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();

	@FXML
	LoginController loginController;
	@FXML
	ChatController chatController;

	@FXML
	public void initialize() {
		loginController.init(this);
		chatController.init(this);
	}

	@FXML
	public void exitApplication(ActionEvent event) {
		Platform.exit();
		server.close();
	}

	public void fromLoginToChat() {
		loginController.paneLoginWindow.toBack();
		new ZoomIn(chatController.paneChatWindow).play();
		chatController.paneChatWindow.toFront();
	}

	public void logMeIn(String ip, int port, String userName) {
		// chatIP = "238.0.0.0"; port = 1234;
		this.userName = userName;
		this.ip = ip;
		this.port = port;
		server = new ServerMain(ip, port, userName, this);
		server.joinGroup();
	}

	public void sendMessageToServer(String message) {
		try {
			message = this.userName + ": " + message;
			byte[] buffer = message.getBytes();
			DatagramPacket datagram;
			datagram = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), port);
			server.send(datagram);
		} catch (UnknownHostException e) {
			System.err.println("Error interacting with Socket.");
			e.printStackTrace();
		}
	}

	public void receiveMessages(String message) {
		chatController.printMessage(message, this.userName);
	}

}
