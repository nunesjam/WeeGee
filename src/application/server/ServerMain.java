package application.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import controller.MainController;
import javafx.application.Platform;

public class ServerMain {

	@SuppressWarnings("unused")
	private String userName;
	private InetAddress chatIP;
	private int port;
	private MulticastSocket socket;
	private MainController main;

	public ServerMain() {
		System.out.println("Enter valid Parameters"); 
	}

	public ServerMain(String chatIP, int port, String userName, MainController mainController) {
		try {
			this.chatIP = InetAddress.getByName(chatIP);
			this.port = port;
			this.userName = userName;
			this.socket = new MulticastSocket(port);
			this.main = mainController;
			System.out.println("CONNECTION OK!");
			System.out.println(chatIP + " IP" + " \n" + port + " PORT" + "\n" + userName + " USERNAME.");

		} catch (IOException e) {
			System.out.println("Error creating socket");
			e.printStackTrace();
		}

	}

	public void joinGroup() {
		try {
			// Time to Live auf 0 auf Localhost -> Intranet würde ich Anzahl der Computer
			// plus 1 setzen.
			this.socket.setTimeToLive(0);
			this.socket.joinGroup(this.chatIP);
			// Thread der nebenläufig läuft und einkommende Nachrichten liest
			Thread threadChat = new Thread(new ReadThread(this.socket, this.chatIP, this.port, this.main));
			threadChat.setName("Message-Receiver");
			threadChat.start();
		} catch (IOException e) {
			System.out.println("Error reading/writing from/to socket");
			e.printStackTrace();
		}
	}

	public void leaveGroup() {
		try {
			this.socket.leaveGroup(this.chatIP);
		} catch (IOException e) {
			System.out.println("Error reading/writing from/to socket");
			e.printStackTrace();
		}
	}

	public void close() {
		this.socket.close();
	}

	public void send(DatagramPacket datagram) {
		try {
			this.socket.send(datagram);
		} catch (IOException e) {
			System.out.println("Error reading/writing from/to socket");
			e.printStackTrace();
		}

	}

}

class ReadThread implements Runnable {
	private MulticastSocket socket;
	private InetAddress group;
	private int port;
	private static final int MAX_LEN = 1000;
	private MainController main;

	ReadThread(MulticastSocket socket, InetAddress group, int port, MainController mainController) {
		this.socket = socket;
		this.group = group;
		this.port = port;
		this.main = mainController;
		System.out.println("THREAD OK!");
	}

	@Override
	public void run() {
		while (true) {
			byte[] buffer = new byte[ReadThread.MAX_LEN];
			DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, port);
			String message;
			try {
				socket.receive(datagram);
				message = new String(buffer, 0, datagram.getLength(), "UTF-8");
				// hier werden nachrichten an den main controller übergeben
				Platform.runLater(() -> {
					main.receiveMessages(message);
				});

			} catch (IOException e) {
				System.out.println("Socket closed!");
			}
		}
	}
}
