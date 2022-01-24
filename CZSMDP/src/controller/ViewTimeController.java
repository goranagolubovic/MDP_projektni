package controller;

import java.net.URL;
import java.util.ResourceBundle;

import control.RestMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewTimeController implements Initializable{
	private Scene previousScene;
	private String previousTitle;
	@FXML
	private ScrollPane scrollPane;
	public  ViewTimeController(Scene scene,String title) {
		previousScene=scene;
		previousTitle=title;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Text text=new Text();
		String content="";
		for(model.Line l:RestMethods.method("GET")) {
			content+=l.toString();
		}
		text.setText(content);
		scrollPane.setContent(text);
	}
	@FXML 
		public void goBack(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setTitle(previousTitle);
        stage.show();
	}

}
