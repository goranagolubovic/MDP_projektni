package controller;

import java.net.URL;
import java.util.ResourceBundle;

import control.XMLSerializer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

public class AddUserController implements Initializable {
	@FXML
	TextField usernameTextField;
	@FXML
	TextField passwordTextField;
	@FXML
	TextField stationTextField;
	
	private Scene previousScene;
	private String previousTitle;
	public AddUserController() {
		
	}
	
	public AddUserController(Scene scene,String title) {
		previousTitle=title;
		previousScene=scene;
	}
	@FXML 
	private void addUser(MouseEvent e) {
		if(!isAnyFieldEmpty()) {
			User user=new User(usernameTextField.getText(),passwordTextField.getText(),stationTextField.getText());
			XMLSerializer.serializeWithXML(user);
			
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	        stage.setScene(previousScene);
	        stage.setTitle(previousTitle);
	        stage.show();
			}
			else {
				Platform.runLater(()->{
					Alert alert=new Alert(AlertType.WARNING);
					alert.setContentText("Provjerite da li su popunjena sva polja pa pokušajte ponovo dodavanje.");
					alert.showAndWait();
				});
			}
	}
	private boolean isAnyFieldEmpty() {
		if(usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || stationTextField.getText().isEmpty()) {
			return true;
		}
		return false;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
