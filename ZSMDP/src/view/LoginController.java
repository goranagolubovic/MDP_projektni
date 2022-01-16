package view;

import java.awt.EventQueue;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import control.LogginSingleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.User;
import service.LoginService;
import service.LoginServiceServiceLocator;
import views.ZSMDPWindow;


public class LoginController implements Initializable{
	@FXML
	private TextField userNameTextField;
	@FXML
	private TextField passwordTextField;
	 public LoginController() {
	    }
	@FXML
	public void login(MouseEvent e) {
		User user=null;
		LoginServiceServiceLocator locator=new LoginServiceServiceLocator();
		try {
			LoginService log=locator.getLoginService();
		    user=log.login(userNameTextField.getText(),String.valueOf(passwordTextField.getText()));
		}
		catch(ServiceException e1) {
			e1.printStackTrace();
		}
		catch(RemoteException e2) {
			e2.printStackTrace();
		}
			if(user!=null) {
				LogginSingleton.getInstance().getActiveUsers().add(user.getUsername());
				System.out.println(LogginSingleton.getInstance().getActiveUsers().size());
			final User finalUser = user;
			}
			else {
				Platform.runLater(new Runnable() {
				      public void run() {
				          Alert alert = new Alert(Alert.AlertType.ERROR);
				          alert.setTitle("");
				          VBox a = new VBox();
				          	a.setStyle("-fx-background-color:  #cdaf3f");
				            a.setPrefHeight(100);
				            a.setPrefWidth(100);
				             Label label = new Label("Netačno korisničko ime ili lozinka!");
				             label.setStyle("-fx-text-fill:  #ffffff");
				             a.getChildren().add(label);
				             alert.getDialogPane().setContent(a);
					         alert.showAndWait();
				      }
				    });
			}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userNameTextField.setStyle("-fx-background-color: -fx-control-inner-background;");
		passwordTextField.setStyle("-fx-background-color: -fx-control-inner-background;");
	}
}
