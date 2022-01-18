package controller;

import java.net.URL;
import java.util.ResourceBundle;

import control.XMLSerializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewUsersController implements Initializable {
	@FXML
	Text usersText;
	
	private Scene previousScene;
	private String previousTitle;
	public ViewUsersController(Scene scene,String title) {
		previousScene=scene;
		previousTitle=title;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usersText.setText(XMLSerializer.showAllUsers());
	}
	@FXML
	private void goBack(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setTitle(previousTitle);
        stage.show();
	}
}
