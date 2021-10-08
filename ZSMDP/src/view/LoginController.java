package view;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import controllers.ZSMDPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
			final User finalUser = user;
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/zsmdp.fxml"));
			ZSMDPController zsmdpController=new ZSMDPController(finalUser.getId());
			loader.setController(zsmdpController);
			try {
				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		        stage.setScene(scene);
		        stage.setTitle(finalUser.getId());
		        stage.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			else {
				System.out.println("Neuspjesno prijavljen!");
			}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
