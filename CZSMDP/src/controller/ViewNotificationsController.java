package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewNotificationsController implements Initializable {
	private Scene previousScene;
	private String previousTitle;
	private String notifications;
	
	@FXML
	Text notificationsText;
	public ViewNotificationsController(Scene scene,String title,String notifications) {
		previousScene=scene;
		previousTitle=title;
		this.notifications=notifications;
	}
	@FXML
	public void goBack(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setTitle(previousTitle);
        stage.show();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		notificationsText.setText(notifications);
	}
}
