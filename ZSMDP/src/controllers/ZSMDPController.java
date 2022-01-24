package controllers;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;
import com.rabbitmq.client.impl.Environment;

import control.LogginSingleton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.FXMain;
import model.User;
import properties.ConfigProperties;
import redis.clients.jedis.exceptions.JedisConnectionException;
import server.AZSMDPInterface;
import server.Report;
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
	ImageView addFileImageView;
	@FXML
	Label userLabel;
	@FXML
	ComboBox<String> locationComboBox;
	@FXML
	ComboBox<String> userComboBox;
	@FXML
	TextField NewMessageTextField;
	@FXML
	TextArea messageTextArea;
	@FXML
	ScrollPane scrollPane;
	private String id;
	private String username;
	private ChatClientSocket chatClientSocket;
	private ConfigProperties configProperties=new ConfigProperties();
	ConcurrentHashMap<String,Boolean> readyMap = new ConcurrentHashMap<>();
	ConcurrentHashMap<String, String> userMessagesMap = new ConcurrentHashMap<>();
	List<String> onlineUsers = new ArrayList<>();
	private String notificationContent = "";
	private Object userComboBoxLocker = new Object();
	private static int NOTIFICATION_PORT;
	private static String NOTIFICATION_HOST;
	private static Logger log = Logger.getLogger(ZSMDPController.class.getName());
	public ZSMDPController(String id, String username) {
		this.id = id;
		this.username = username;
		this.chatClientSocket = new ChatClientSocket(username);
		onlineUsers.add(username);
		NOTIFICATION_PORT=Integer.valueOf(configProperties.getProperties().getProperty("NOTIFICATION_PORT"));
		NOTIFICATION_HOST=configProperties.getProperties().getProperty("NOTIFICATION_HOST");
		
	}

	public ZSMDPController() {

	}

	@FXML
	private void viewTime(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("VIEW_TIME")));
		Parent root = null;
		ViewTimeController viewTimeController = new ViewTimeController(viewTimeImage.getScene(), id);
		loader.setController(viewTimeController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(id);
		stage.show();
	}

	@FXML
	private void addTime(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("ADD_TIME")));
		Parent root = null;
		AddTimeController addTimeController = new AddTimeController(addTimeImage.getScene(), id);
		loader.setController(addTimeController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(id);
		stage.show();
	}

	@FXML
	private void logout(MouseEvent e) {
		PrintWriter out = chatClientSocket.out;
		out.println("EXIT:"+id+"#"+username);
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userLabel.setText(username);
		String[] stations = {};
		LoginServiceServiceLocator locator = new LoginServiceServiceLocator();
		try {
			LoginService log = locator.getLoginService();
			stations = log.getStations();
			locationComboBox.getItems().addAll(stations);
			locationComboBox.getSelectionModel().select(getIndexOfCurrentStation(stations));
			chatClientSocket.addSocketInMap(id);
			
			String[] usernames=log.users();
			for(int i=0;i<usernames.length;i++) {
				readyMap.put(usernames[i],false);
			}
			initializeUserComboBox(null);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e,()->e.getMessage());
			}
		} catch (ServiceException e1) {
			Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING,e1,()->e1.getMessage());
		} catch (RemoteException e2) {
			Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e2,()->e2.getMessage());
		}
		listenForMessages();
		listenForNotifications();
		userComboBox.valueProperty().addListener((ov,previous,current)->{
					if(!"".equals(current) && current!=null) {
						System.out.println(current);
							boolean ready=readyMap.get(userComboBox.getValue());
							if (ready) {
								Platform.runLater(() -> {
									messageTextArea.setText(userMessagesMap.get(userComboBox.getValue()));
								});
							}
							else {
								Platform.runLater(() -> {
									messageTextArea.setText("");
								});
								new Thread(() -> ChatClientSocket.connect(id, username, locationComboBox.getValue(), userComboBox.getValue()))
									.start();
							}
						}
		});
	}

	private void listenForNotifications() {
		new Thread(() -> {
			MulticastSocket socket = null;
			byte[] buffer = new byte[256];
			try {
				socket = new MulticastSocket(NOTIFICATION_PORT);
				InetAddress address = InetAddress.getByName(NOTIFICATION_HOST);
				socket.joinGroup(address);
				while (true) {
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					String received = new String(packet.getData(), 0, packet.getLength());
					if (!received.startsWith(username)) {
						notificationContent += received + "\n";
						Text text = new Text();
						text.setText(notificationContent);

						Platform.runLater(() -> {
							scrollPane.setContent(text);
						});
					}
				}
			} catch (IOException ioe) {
				Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, ioe.fillInStackTrace().toString());
			}
		}).start();
	}

	private int getIndexOfCurrentStation(String[] stations) {
		return Arrays.asList(stations).indexOf(id);
	}

	@FXML
	public void initializeUserComboBox(ActionEvent e) {
			LoginServiceServiceLocator locator = new LoginServiceServiceLocator();
			LoginService log;
			PrintWriter out = chatClientSocket.out;
			try {
				log = locator.getLoginService();
				userComboBox.getItems().clear();
				userComboBox.setValue("");
				synchronized(userComboBoxLocker) {
					userComboBoxLocker.notify();
				}
				out.println("SEND ONLINE USERS:" + id + "#" + username + ":" + locationComboBox.getValue());
			} catch (ServiceException e1) {
				Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
			}
	}

	public void listenForMessages() {
		new Thread(() -> {
			BufferedReader in = chatClientSocket.in;
			PrintWriter out = chatClientSocket.out;
			while (true) {
				try {
					String msg = in.readLine();
						if (msg != null && !msg.equals("")) {
							if (msg.startsWith("ONLINE USERS")) {
								String users = msg.split(":")[1];
								String usersGroup[]=users.split(";");
								if(!"empty".equals(users)) {
								userComboBox.getItems().addAll(usersGroup);
								}
							}
							else if (msg.startsWith("NOT CONNECTED")) {
								Platform.runLater(() -> {
									Alert alert=new Alert(AlertType.WARNING);
									alert.setContentText("Korisnik "+msg.split(":")[1]+" nije aktivan.");
									alert.showAndWait();
								});

							}
							else {
							// potvrdjivanje komunikacije sa korisnikom
							if (msg.startsWith("CHAT")) {
								String senderInfo = msg.split(":")[1];
								String senderId = senderInfo.split("#")[0];
								String senderUsername = senderInfo.split("#")[1];

								String receiverInfo = msg.split(":")[2];
								String receiverId = receiverInfo.split("#")[0];
								String receiverUsername = receiverInfo.split("#")[1];

								Platform.runLater(() -> {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setContentText("Da li želite da uspostavite komunikaciju sa korisnikom "
											+ senderUsername + "?");
									final Optional<ButtonType> result = alert.showAndWait();
									if (result.get() == ButtonType.OK) {
										userComboBox.setValue("");
										CountDownLatch signal = new CountDownLatch(1);
										new Thread(() -> {
											System.out.println("Non-UI thread");
											out.println("VALID CONNECTION:" + receiverInfo + ":" + senderInfo);
											userMessagesMap.put(senderInfo, "");
											readyMap.put(senderUsername, true);
											signal.countDown();
										}).start();
										try {
											signal.await();
										} catch (InterruptedException e) {
											Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING,e.fillInStackTrace().toString());
										}
										locationComboBox.setValue(senderId);
										userComboBox.setValue(senderUsername);
									}
								});

							} else if (msg.startsWith("VALID CONNECTION")) {
								String senderInfo = msg.split(":")[1];
								String senderId = senderInfo.split("#")[0];
								String senderUsername = senderInfo.split("#")[1];

								String receiverInfo = msg.split(":")[2];
								String receiverId = receiverInfo.split("#")[0];
								String receiverUsername = receiverInfo.split("#")[1];
								if (!userMessagesMap.containsKey(senderInfo)) {
									userMessagesMap.put(senderInfo, "");
								}
								readyMap.put(senderUsername, true);
							}
							else if (msg.startsWith("MESSAGE")) {
								String senderInfo = msg.split(":")[1];
								String senderId = senderInfo.split("#")[0];
								String senderUsername = senderInfo.split("#")[1];

								String receiverInfo = msg.split(":")[2];
								String receiverId = receiverInfo.split("#")[0];
								String receiverUsername = receiverInfo.split("#")[1];
								String newMessage = senderUsername + ": " + msg.split(":")[3] + "\n";
								final String messages = userMessagesMap.get(senderInfo) + newMessage;
								userMessagesMap.replace(senderInfo, messages);
								Platform.runLater(() -> {
									messageTextArea.setText(userMessagesMap.get(senderInfo));
								});
							} else if (msg.startsWith("FILE")) {
								String senderInfo = msg.split(":")[1];
								String senderId = senderInfo.split("#")[0];
								String senderUsername = senderInfo.split("#")[1];

								String receiverInfo = msg.split(":")[2];
								String receiverId = receiverInfo.split("#")[0];
								String receiverUsername = receiverInfo.split("#")[1];
								Wrapper wrapper = new Wrapper();
								String fileName = msg.split(":")[3];
								String fileContent = msg.split(":")[4];
								byte[] fileContentBytes = Base64.getDecoder().decode(fileContent);
								String newMessage = senderUsername + ": " + fileName + "\n";
								final String messages = userMessagesMap.get(senderInfo) + newMessage;
								userMessagesMap.replace(senderInfo, messages);
								CountDownLatch countDownLatch = new CountDownLatch(1);
								Platform.runLater(() -> {
									Alert a = new Alert(AlertType.CONFIRMATION);
									a.setContentText("Da li želite da preuzmete fajl " + fileName + "?");
									final Optional<ButtonType> result = a.showAndWait();
									if (result.get() == ButtonType.OK) {
										wrapper.value = true;
										messageTextArea.setText(userMessagesMap.get(senderInfo));
									}
									countDownLatch.countDown();
								});
								try {
									countDownLatch.await();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								if (wrapper.value) {
									String filePath = configProperties.getProperties().getProperty("DOWNLOAD_FOLDER") + File.separator + receiverUsername;
									File file = new File(filePath);
									if (!file.exists()) {
										file.mkdir();
									}
									try {
										Files.write(Paths.get(filePath + File.separator + fileName), fileContentBytes);
										Platform.runLater(() -> {
											Alert a = new Alert(AlertType.INFORMATION);
											a.setContentText("Uspješno ste preuzeli fajl " + fileName);
											a.show();
										});
									} catch (IOException e) {
										Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
									}
								}
							}

						}
					}
				} catch (IOException e) {
					Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
			}
		}).start();
	}
	@FXML
	private void sendMessage(MouseEvent e) {
		boolean ready=readyMap.get(userComboBox.getValue());
		if (!ready) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Niste dobili dozvolu za chat. 😒😒");
				alert.showAndWait();
			});
			return;
		}
		new Thread(() -> {
			PrintWriter out = chatClientSocket.out;
			String receiverId = locationComboBox.getValue();
			String receiver = userComboBox.getValue();
			String message = NewMessageTextField.getText();
			String receiverInfo = receiverId + "#" + receiver;
			final String messages = userMessagesMap.get(receiverInfo) + username + ":" + message + "\n";
			userMessagesMap.replace(receiverInfo, messages);
			Platform.runLater(() -> {
				messageTextArea.setText(userMessagesMap.get(receiverInfo));
				NewMessageTextField.clear();
			});
			if (!isMessageFile(message)) {
				out.println(String.join(":", "MESSAGE", id + "#" + username, receiverId + "#" + receiver, message));
			} else {
				File f = new File(message);
				FileInputStream fis;
				if (f.length() > 0) {
					try {
						fis = new FileInputStream(f);
						byte[] fileContentBytes = new byte[(int) f.length()];
						fis.read(fileContentBytes);
						String fileContent = Base64.getEncoder().encodeToString(fileContentBytes);
						out.println(String.join(":", "FILE", id + "#" + username, receiverId + "#" + receiver,
								f.getName(), fileContent));
					} catch (IOException e1) {
						Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
					}
				}
			}
		}).start();
	}

	private boolean isMessageFile(String message) {
		try {
			return new File(message).exists();
		} catch (Exception ex) {
			return false;
		}
	}

	@FXML
	private void chooseFile(MouseEvent e) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(addFileImageView.getScene().getWindow());
		if (file != null) {
			NewMessageTextField.setText(file.getAbsolutePath());
		}
	}

	@FXML
	private void sendNotification(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("VIEW_NOTIFICATION")));
		Parent root = null;
		NotificationController notificationController = new NotificationController(viewTimeImage.getScene(), id,username);
		loader.setController(notificationController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(configProperties.getProperties().getProperty("CSS"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(id);
		stage.show();
	}
	@FXML
	private void sendReport(MouseEvent e) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(addFileImageView.getScene().getWindow());
		if (file != null && file.getName().endsWith(".pdf")) {
			System.setProperty("java.security.policy", configProperties.getProperties().getProperty("CLIENT_POLICYFILE"));
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

				try {
					AZSMDPInterface azsmdpServer=(AZSMDPInterface) Naming.lookup( configProperties.getProperties().getProperty("NAMING_PATH"));
					try {
						azsmdpServer.uploadReport(new Report(
								file.getName(),
								Files.readAllBytes(file.toPath()),
								username,
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")),
								file.length()
						));
					} catch (IOException e1) {
						Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
					}
				} catch (RemoteException | NotBoundException | MalformedURLException e1) {
					Logger.getLogger(ZSMDPController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
				}
				
	}
	}
	class Wrapper {
		public boolean value;
	}
}
