package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.rpc.ServiceException;

import com.rabbitmq.client.impl.Environment;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import service.LoginService;
import service.LoginServiceServiceLocator;

public class ZSMDPController implements Initializable {
	@FXML 
	ImageView viewTimeImage;
	@FXML
	ImageView addTimeImage;
	@FXML
	ImageView logoutImage;
	@FXML
	Label userLabel;
	@FXML
	ComboBox<String> locationComboBox;
	@FXML
	ComboBox<String>userComboBox;
	@FXML 
	TextField NewMessageTextField;
	@FXML 
	TextArea MessageTextArea;
	
	private String id;
	private String username;
	private ChatClientSocket chatClientSocket;
	public ZSMDPController(String id,String username) {
		this.id=id;
		this.username=username;
		this.chatClientSocket=new ChatClientSocket(username);
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
		this.chatClientSocket.endSession();
		System.exit(0);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userLabel.setText(username);
		String[] stations= {};
		LoginServiceServiceLocator locator=new LoginServiceServiceLocator();
		try {
			LoginService log=locator.getLoginService();
		    stations=log.getStations();
		    locationComboBox.getItems().addAll(stations);
		    locationComboBox.getSelectionModel().select(getIndexOfCurrentStation(stations));
		    initializeUserComboBox(null);
		   // userComboBox.getItems().addListener(log.getUsersForSelectedStation(locationComboBox.getValue())
		    		//,userComboBox.getItems(),log.getUsersForSelectedStation(locationComboBox.getValue()));
		}
		catch(ServiceException e1) {
			e1.printStackTrace();
		}
		catch(RemoteException e2) {
			e2.printStackTrace();
		}
		listenForMessages();
	}
	private int getIndexOfCurrentStation(String[]stations) {
		return Arrays.asList(stations).indexOf(id);
	}
	@FXML
	public void initializeUserComboBox(ActionEvent e) {
		LoginServiceServiceLocator locator=new LoginServiceServiceLocator();
			LoginService log;
			try {
				log = locator.getLoginService();
				userComboBox.getItems().clear();
				userComboBox.getItems().addAll(log.getUsersForSelectedStation(locationComboBox.getValue()));
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}    
		
	}
	public  void listenForMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						BufferedReader in=chatClientSocket.getIn();
						String msg=in.readLine();
						if(msg!=null)
							MessageTextArea.appendText(msg+"\n");
							//MessageTextField.setText(msg);
					} catch (IOException  e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	@FXML 
	public void selectUser(ActionEvent e) {
		//ChatClientSocket ch=new ChatClientSocket();
		System.out.println("User is selected");
		new Thread() {
			public void run() {
				chatClientSocket.connect(username,userComboBox.getValue());
			}
			}.start();
	}
	@FXML 
	private void sendMessage(MouseEvent e) {
		ChatClientSocket ch=new ChatClientSocket(userComboBox.getValue());
		new Thread() {
			public void run() {
				chatClientSocket.sendMessage(NewMessageTextField.getText(),userComboBox.getValue());
			}
		}.start();
	}
	
}
