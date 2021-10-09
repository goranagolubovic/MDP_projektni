package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ZSMDPController implements Initializable {
	@FXML 
	ImageView viewTimeImage;
	@FXML
	ImageView addTimeImage;
	@FXML
	ImageView logoutImage;
	@FXML
	Label userLabel;
	private String id;
	private String username;
	public ZSMDPController(String id,String username) {
		this.id=id;
		this.username=username;
	}
	public ZSMDPController() {
		
	}
	@FXML
	private void viewTime(MouseEvent e) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/viewTime.fxml"));
		Parent root=null;
		ViewTimeController viewTimeController=new ViewTimeController(viewTimeImage.getScene(),id);
		loader.setController(viewTimeController);
		 try {
	            root = loader.load();
	            
	        } catch (IOException e1) {
	        	e1.printStackTrace();
	            //Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
	            //Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
	        }
		 	Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.setTitle(id);
	        stage.show();
	}
	@FXML 
	private void addTime(MouseEvent e) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/addTime.fxml"));
		Parent root=null;
		AddTimeController addTimeController=new AddTimeController(addTimeImage.getScene(),id);
		loader.setController(addTimeController);
		 try {
	            root = loader.load();
	            
	        } catch (IOException e1) {
	        	e1.printStackTrace();
	            //Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
	            //Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
	        }
		 	Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.setTitle(id);
	        stage.show();
	}
	@FXML 
	private void logout(MouseEvent e) {
		System.exit(0);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userLabel.setText(username);
		
	}
}
