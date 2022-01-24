package controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.rpc.ServiceException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.FXMain;
import model.User;
import properties.ConfigProperties;
import service.LoginService;
import service.LoginServiceServiceLocator;


public class LoginController implements Initializable{
	@FXML
	private TextField userNameTextField;
	@FXML
	private TextField passwordTextField;
	private static ConfigProperties configProperties=new ConfigProperties();
	 public LoginController() {
	    }
	@FXML
	public void login(MouseEvent e) {
		//Logger.getLogger(LoginController.class.getName()).addHandler(FXMain.handler);
		User user=null;
		LoginServiceServiceLocator locator=new LoginServiceServiceLocator();
		try {
			LoginService log=locator.getLoginService();
		    user=log.login(userNameTextField.getText(),String.valueOf(passwordTextField.getText()));
		}
		catch(ServiceException e1) {
			Logger.getLogger(LoginController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		catch(RemoteException e2) {
			Logger.getLogger(LoginController.class.getName()).log(Level.WARNING, e2.fillInStackTrace().toString());
		}
			if(user!=null) {
				System.out.println("Uspjesno prijavljen!");
				final User finalUser = user;
				FXMLLoader loader=new FXMLLoader(getClass().getResource( configProperties.getProperties().getProperty("ZSMDP")));
				ZSMDPController zsmdpController=new ZSMDPController(finalUser.getId(),finalUser.getUsername());
				loader.setController(zsmdpController);
				try {
					Parent root = (Parent) loader.load();
					Scene scene = new Scene(root);
					Stage stage= (Stage) userNameTextField.getScene().getWindow();
			        stage.setScene(scene);
			        stage.setTitle(finalUser.getId());
			        stage.show();
				} catch (IOException e1) {
					Logger.getLogger(LoginController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
				}
				}
				else {
					Platform.runLater(()->{
						Alert alert=new Alert(AlertType.ERROR);
						alert.setContentText("Netačno korisničko ime ili lozinka.Pokušajte ponovo.");
						alert.showAndWait();
					});
				}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userNameTextField.setStyle("-fx-background-color: -fx-control-inner-background;");
		passwordTextField.setStyle("-fx-background-color: -fx-control-inner-background;");
	}
}
