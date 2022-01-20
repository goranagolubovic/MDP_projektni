package controller;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import control.XMLSerializer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import server.AZSMDPInterface;

public class CZSMDPController implements Initializable{
	@FXML
	ImageView addUserImageView;
	@FXML
	ImageView viewNotificationsImageView;
	private static final int NOTIFICATION_PORT = 20000;
	private static final String NOTIFICATION_HOST = "224.0.0.11";
	
	private String notificationContent="";

	private boolean isNotificationArrived=false;
	
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
		stage.setTitle("View schedule");
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
		stage.setTitle("Delete line");
		stage.show();
	}
	@FXML
	private void createSchedule(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/createSchedule.fxml"));
		Parent root = null;
		CreateScheduleController createScheduleController = new CreateScheduleController(addUserImageView.getScene(),stage.getTitle());
		loader.setController(createScheduleController);
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
		stage.setTitle("Create schedule");
		stage.show();
	}
	@FXML
	private void viewNotifications(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewNotifications.fxml"));
		Parent root = null;
		ViewNotificationsController viewNotificationsController = new ViewNotificationsController(addUserImageView.getScene(),stage.getTitle(),notificationContent);
		loader.setController(viewNotificationsController);
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
			//String notificationContent="";
			MulticastSocket socket = null;
			byte[] buffer = new byte[256];
			try {
				socket = new MulticastSocket(NOTIFICATION_PORT);
				InetAddress address = InetAddress.getByName(NOTIFICATION_HOST);
				socket.joinGroup(address);
				while (true) {
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					if(packet.getLength()!=0) {
						/*(isNotificationArrived=true;
						while(isNotificationArrived) {
							String currentWorkingDir = System.getProperty("user.dir");
					        System.out.println(currentWorkingDir);
							Image image=new Image("file:\\images\\left-bell.png");
							viewNotificationsImageView.setImage(new Image("file:\\images\\left-bell.png"));
							Thread.sleep(500);
							viewNotificationsImageView.setImage(new Image("file:\\images\\bell.png"));
							Thread.sleep(500);
							viewNotificationsImageView.setImage(new Image("file:\\images\\right-bell.png"));
							Thread.sleep(500);
							viewNotificationsImageView.setImage(new Image("file:\\images\\bell.png"));
							Thread.sleep(500);
							
						}*/
					socket.receive(packet);
					String received = new String(packet.getData(), 0, packet.getLength());
					notificationContent += received.split(":")[1] + "\n";
					}
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}).start();
	}
	@FXML
	private void downloadReport(MouseEvent e) {
		String PATH = "resources";
		String DOWNLOAD_PATH="downlaoding";
			System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

				String name = "AZSMDP Server";
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry(1099);
					AZSMDPInterface azsmdpServer=(AZSMDPInterface) registry.lookup(name);
					azsmdpServer.downloadReport(DOWNLOAD_PATH);
					UnicastRemoteObject.unexportObject(registry, true);

				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//AZSMDPInterface onlineShop = (AZSMDPInterface)
				
	}
}
