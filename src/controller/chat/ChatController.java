package controller.chat;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import application.chat.UserStatusListener;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ChatController {

	private MainController main;
	
	@FXML
	public AnchorPane paneChatWindow;
	@FXML
	public ListView<UserStatusListener> listActiveUser;
	@FXML
	public TextArea txtMessages;
	@FXML
	public ListView <String> listMessages;
	@FXML
	private Button btnSend;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource().equals(btnSend)) {
			String message = txtMessages.getText();
			main.sendMessageToServer(message);
			txtMessages.clear();
		}
	}

	@FXML
	private void handleKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			String message = txtMessages.getText();
			main.sendMessageToServer(message);
			txtMessages.clear();
		}
	}

	public void init(MainController mainController) {
		this.main = mainController;
	}

	public void printMessage(String message, String userName) {
		String[] splitString = StringUtils.split(message, ":", 2);
		if (userName.equals(splitString[0])) {
			System.out.println(Arrays.toString(splitString));	
			listMessages.getItems().add(message);
		}

	}
}
