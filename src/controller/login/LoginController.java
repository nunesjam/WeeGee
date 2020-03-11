package controller.login;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {

	private MainController main;

	@FXML
	public AnchorPane paneLoginWindow;
	@FXML
	private TextField txtUsername, txtPort, txtChatIp;
	@FXML
	private Button btnJoinServer;

	@FXML
	public void joinServer(ActionEvent event) {
		if (event.getSource().equals(btnJoinServer)) {
			String userName = txtUsername.getText().trim();
			String ip = txtChatIp.getText().trim();
			int port = Integer.parseInt(txtPort.getText().trim());

			main.fromLoginToChat();
			main.logMeIn(ip, port, userName);
		}

	}

	public void init(MainController mainController) {
		this.main = mainController;
	}
}
