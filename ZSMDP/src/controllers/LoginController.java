package controllers;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.User;
import service.LoginService;
import service.LoginServiceServiceLocator;
import views.ZSMDPWindow;


public class LoginController {
	@FXML
	private TextField userNameTextField;
	@FXML
	private TextField passwordTextField;
	 public LoginController() {
	    }
	@FXML
	public void login(ActionEvent e) {
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
				
			System.out.println("Uspjesno prijavljen!");
			System.out.println(user.getId());
			final User finalUser = user;

			}
			else {
				System.out.println("Neuspjesno prijavljen!");
			}
	}
}
