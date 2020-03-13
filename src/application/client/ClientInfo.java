package application.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientInfo {
	private String userName;
	private InetAddress chatIP;
	private int port;
	private MulticastSocket socket;
	
	public ClientInfo() {
		setUserName(userName);
		setChatIP(chatIP);
		setPort(port);
		setSocket(socket);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public InetAddress getChatIP() {
		return chatIP;
	}
	public void setChatIP(InetAddress chatIP) {
		this.chatIP = chatIP;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public MulticastSocket getSocket() {
		return socket;
	}
	public void setSocket(MulticastSocket socket) {
		try {
			this.socket = new MulticastSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
