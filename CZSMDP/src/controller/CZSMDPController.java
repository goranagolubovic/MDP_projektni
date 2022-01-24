package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.FXMain;
import properties.ConfigProperties;
import server.AZSMDPInterface;

public class CZSMDPController implements Initializable{
	@FXML
	ImageView addUserImageView;
	@FXML
	ImageView viewNotificationsImageView;
	private static int NOTIFICATION_PORT;
	private static String NOTIFICATION_HOST ;
	private static ConfigProperties configProperties=new ConfigProperties();
	private String notificationContent="";

	private boolean isNotificationArrived=false;
	
	public CZSMDPController() {
		NOTIFICATION_HOST=configProperties.getProperties().getProperty("NOTIFICATION_HOST");
		NOTIFICATION_PORT=Integer.valueOf(configProperties.getProperties().getProperty("NOTIFICATION_PORT"));
	}
	@FXML
	private void addUser(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("ADD_USER")));
		Parent root = null;
		AddUserController addUserController = new AddUserController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(addUserController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Add user");
		stage.show();
	}
	@FXML
	private void deleteUser(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("DELETE_USER")));
		Parent root = null;
		DeleteUserController deleteUserController = new DeleteUserController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(deleteUserController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Delete user");
		stage.show();
	}
	@FXML
	private void viewUsers(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("VIEW_USERS")));
		Parent root = null;
		ViewUsersController viewUsersController = new ViewUsersController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(viewUsersController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("View users");
		stage.show();
	}
	@FXML
	private void viewTimeSchedule(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("VIEW_TIME")));
		Parent root = null;
		ViewTimeController viewTimeController = new ViewTimeController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(viewTimeController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("View schedule");
		stage.show();
	}
	@FXML
	private void deleteLine(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("DELETE_LINE")));
		Parent root = null;
		DeleteLineController deleteLineController = new DeleteLineController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(deleteLineController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Delete line");
		stage.show();
	}
	@FXML
	private void createSchedule(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("CREATE_SCHEDULE")));
		Parent root = null;
		CreateScheduleController createScheduleController = new CreateScheduleController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(createScheduleController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Create schedule");
		stage.show();
	}
	@FXML
	private void viewNotifications(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("VIEW_NOTIFICATIONS")));
		Parent root = null;
		ViewNotificationsController viewNotificationsController = new ViewNotificationsController(addUserImageView.getScene(),stage.getTitle(),notificationContent);
		loader.setController(viewNotificationsController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("View notifications");
		stage.show();
		isNotificationArrived=false;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listenForNotificatons();
	}
	private void listenForNotificatons() {
		new Thread(()->{
			MulticastSocket socket = null;
			byte[] buffer = new byte[256];
			try {
				socket = new MulticastSocket(NOTIFICATION_PORT);
				InetAddress address = InetAddress.getByName(NOTIFICATION_HOST);
				socket.joinGroup(address);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true) {
					socket.receive(packet);
					String received = new String(packet.getData(), 0, packet.getLength());
					notificationContent += received + "\n";
					if(packet.getLength()!=0) {
						isNotificationArrived=true;
						while(isNotificationArrived) {
							viewNotificationsImageView.setImage(new Image(new FileInputStream(configProperties.getProperties().getProperty("LEFT_BELL"))));
							Thread.sleep(200);
							viewNotificationsImageView.setImage(new Image(new FileInputStream(configProperties.getProperties().getProperty("BELL"))));
							Thread.sleep(200);
							viewNotificationsImageView.setImage(new Image(new FileInputStream(configProperties.getProperties().getProperty("RIGHT_BELL"))));
							Thread.sleep(200);
							viewNotificationsImageView.setImage(new Image(new FileInputStream(configProperties.getProperties().getProperty("BELL"))));
							Thread.sleep(200);
							
						}
					}
				}
			} catch (IOException | InterruptedException ioe) {
				Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, ioe.fillInStackTrace().toString());
			}
		}).start();
	}
	@FXML
	private void downloadReport(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/downloadReport.fxml"));
		Parent root = null;
			System.setProperty("java.security.policy", configProperties.getProperties().getProperty("CLIENT_POLICYFILE"));
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			AZSMDPInterface azsmdpServer;
			try {
				azsmdpServer = (AZSMDPInterface) Naming.lookup(configProperties.getProperties().getProperty("NAMING_PATH"));
				List<String> fileNames = azsmdpServer.getReportNames();	
				DownloadReportController downloadReportController = new DownloadReportController(addUserImageView.getScene(),stage.getTitle(),fileNames);
				loader.setController(downloadReportController);
				try {
					root = loader.load();

				} catch (IOException e1) {
					Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
				}
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Download report");
				stage.show();
			} catch (MalformedURLException e2) {
				Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e2.fillInStackTrace().toString());
			} catch (RemoteException e2) {
				Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e2.fillInStackTrace().toString());
			} catch (NotBoundException e2) {
				Logger.getLogger(CZSMDPController.class.getName()).log(Level.WARNING, e2.fillInStackTrace().toString());
			}
	}
}
