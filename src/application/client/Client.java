package application.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import application.server.ServerMain;

public class Client extends ClientInfo {

	ClientInfo clientInformation;
	ServerMain server;

	public Client(MulticastSocket socket, InetAddress chatIP, int port, String userName) {
		this.clientInformation = new ClientInfo();
		clientInformation.setPort(port);
		clientInformation.setChatIP(chatIP);
		clientInformation.setSocket(socket);
		clientInformation.setUserName(userName);
	}

	public void joinServer() {
		try {
			clientInformation.getSocket().joinGroup(clientInformation.getChatIP());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void leaveServer() {
		try {
			clientInformation.getSocket().leaveGroup(clientInformation.getChatIP());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendMessageToServer(DatagramPacket datagram) {
		try {
			clientInformation.getSocket().send(datagram);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
