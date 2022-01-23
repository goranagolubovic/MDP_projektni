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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
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
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true) {
					socket.receive(packet);
					if(packet.getData().length!=0) {
						isNotificationArrived=true;
						while(isNotificationArrived) {
							viewNotificationsImageView.setImage(new Image(new FileInputStream("images\\left-bell.png")));
							Thread.sleep(200);
							viewNotificationsImageView.setImage(new Image(new FileInputStream("images\\bell.png")));
							Thread.sleep(200);
							viewNotificationsImageView.setImage(new Image(new FileInputStream("images\\right-bell.png")));
							Thread.sleep(200);
							viewNotificationsImageView.setImage(new Image(new FileInputStream("images\\bell.png")));
							Thread.sleep(200);
							
						}
					socket.receive(packet);
					String received = new String(packet.getData(), 0, packet.getLength());
					notificationContent += received.split(":")[1] + "\n";
					}
				}
			} catch (IOException | InterruptedException ioe) {
				System.out.println(ioe);
			}
		}).start();
	}
	@FXML
	private void downloadReport(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/downloadReport.fxml"));
		Parent root = null;
		String PATH = "resources";
			System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			AZSMDPInterface azsmdpServer;
			try {
				azsmdpServer = (AZSMDPInterface) Naming.lookup("rmi://127.0.0.1:1099/AZSMDPServer");
				List<String> fileNames = azsmdpServer.getReportNames();	
				DownloadReportController downloadReportController = new DownloadReportController(addUserImageView.getScene(),stage.getTitle(),fileNames);
				loader.setController(downloadReportController);
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
				stage.setTitle("Download report");
				stage.show();
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NotBoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		/*String PATH = "resources";
		String DOWNLOAD_PATH="downlaoding";
			System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			AZSMDPInterface azsmdpServer;
			try {
				azsmdpServer = (AZSMDPInterface) Naming.lookup("rmi://127.0.0.1:1099/AZSMDPServer");
				azsmdpServer.downloadReport(DOWNLOAD_PATH);	
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NotBoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}*/
				
	}
}
