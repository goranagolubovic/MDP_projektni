package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import control.RestMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewTimeController implements Initializable{
	private String id;
	private Scene previousScene;
	@FXML
	private ScrollPane scrollPane;
	public  ViewTimeController(Scene previousScene,String id) {
		this.id=id;
		this.previousScene=previousScene;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Text text=new Text();
		String content="";
		for(model.Line l:RestMethods.method("GET", id)) {
			content+=l.toString();
		}
		text.setText(content);
		scrollPane.setContent(text);
	}
	@FXML 
		public void goBack(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();
	}

}
