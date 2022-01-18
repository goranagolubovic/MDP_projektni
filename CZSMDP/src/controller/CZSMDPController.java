package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import control.XMLSerializer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

public class CZSMDPController implements Initializable{
	@FXML
	ImageView addUserImageView;
	
	public CZSMDPController() {
		
	}
	@FXML
	private void addUser(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addUser.fxml"));
		Parent root = null;
		AddUserController addUserController = new AddUserController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(addUserController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Add user");
		stage.show();
		
	}
	@FXML
	private void deleteUser(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteUser.fxml"));
		Parent root = null;
		DeleteUserController deleteUserController = new DeleteUserController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(deleteUserController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Delete user");
		stage.show();
	}
	@FXML
	private void viewUsers(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewUsers.fxml"));
		Parent root = null;
		ViewUsersController viewUsersController = new ViewUsersController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(viewUsersController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("View users");
		stage.show();
	}
	@FXML
	private void viewTimeSchedule(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewTime.fxml"));
		Parent root = null;
		ViewTimeController viewTimeController = new ViewTimeController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(viewTimeController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("View time schedule");
		stage.show();
	}
	@FXML
	private void deleteLine(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteLine.fxml"));
		Parent root = null;
		DeleteLineController deleteLineController = new DeleteLineController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(deleteLineController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("View time schedule");
		stage.show();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
