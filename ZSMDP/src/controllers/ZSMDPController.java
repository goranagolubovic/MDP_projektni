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
import model.User;
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
	private String filePath;
	private boolean isMessageFile = false;

	private String downloadFolder = "files";

	ConcurrentHashMap<String,Boolean> readyMap = new ConcurrentHashMap<>();
	ConcurrentHashMap<String, String> userMessagesMap = new ConcurrentHashMap<>();
	List<String> onlineUsers = new ArrayList<>();

	private String notificationContent = "";
	private Object userComboBoxLocker = new Object();
	private String previousUserComboBoxValue="";
	private boolean isNewConnectionInitiated=true;
	private volatile boolean isChatRequestProcedeed;

	private static final int NOTIFICATION_PORT = 20000;
	private static final String NOTIFICATION_HOST = "224.0.0.11";

	public ZSMDPController(String id, String username) {
		this.id = id;
		this.username = username;
		this.chatClientSocket = new ChatClientSocket(username);
		onlineUsers.add(username);
	}

	public ZSMDPController() {

	}

	@FXML
	private void viewTime(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewTime.fxml"));
		Parent root = null;
		ViewTimeController viewTimeController = new ViewTimeController(viewTimeImage.getScene(), id);
		loader.setController(viewTimeController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		System.out.println(loader.getLocation());
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(id);
		stage.show();
	}

	@FXML
	private void addTime(MouseEvent e) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addTime.fxml"));
		Parent root = null;
		AddTimeController addTimeController = new AddTimeController(addTimeImage.getScene(), id);
		loader.setController(addTimeController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// userComboBox.getItems().addListener(log.getUsersForSelectedStation(locationComboBox.getValue()),userComboBox.getItems(),log.getUsersForSelectedStation(locationComboBox.getValue()));
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		listenForMessages();
		listenForNotifications();
		userComboBox.valueProperty().addListener((ov,previous,current)->{
				//Platform.runLater(()->{
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
		//checkIfUserComboBoxIsNotEmpty();
		//actionUserComboBoxListener();
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
						notificationContent += received.split(":")[1] + "\n";
						Text text = new Text();
						text.setText(notificationContent);

						Platform.runLater(() -> {
							scrollPane.setContent(text);
						});
					}
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}).start();
	}

	private int getIndexOfCurrentStation(String[] stations) {
		return Arrays.asList(stations).indexOf(id);
	}

	@FXML
	public void initializeUserComboBox(ActionEvent e) {
			isChatRequestProcedeed=false;
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	public void listenForMessages() {
		new Thread(() -> {
			BufferedReader in = chatClientSocket.in;
			PrintWriter out = chatClientSocket.out;
			while (true) {
				try {
					String msg = in.readLine();
						if (msg != null && !msg.startsWith("ALL")) {
							if (msg.startsWith("ONLINE USERS")) {
								String users = msg.split(":")[1];
								// List<String> onlineUsers=Arrays.asList(users.split("*"));
								// List<String> olineUsersForSelectedStation=onlineUsers.stream()
								// .filter(u->u.startsWith(locationComboBox.getValue()))
								// .collect(Collectors.toList());
								// Platform.runLater(()->{
								if(!"empty".equals(users)) {
								//userComboBox.setItems(users);
								userComboBox.getItems().addAll(users);
								}
								// });
							}
							else if (msg.startsWith("NOT CONNECTED")) {
								Platform.runLater(() -> {
									Alert alert=new Alert(AlertType.WARNING);
									alert.setContentText("Korisnik "+msg.split(":")[1]+" nije aktivan.");
									alert.showAndWait();
								});

							}
							else {
							String senderInfo = msg.split(":")[1];
							String senderId = senderInfo.split("#")[0];
							String senderUsername = senderInfo.split("#")[1];

							String receiverInfo = msg.split(":")[2];
							String receiverId = receiverInfo.split("#")[0];
							String receiverUsername = receiverInfo.split("#")[1];

							// potvrdjivanje komunikacije sa korisnikom
							if (msg.startsWith("CHAT")) {
								isChatRequestProcedeed=true;
								Platform.runLater(() -> {
									Alert alert = new Alert(AlertType.CONFIRMATION);
									alert.setContentText("Da li želite da uspostavite komunikaciju sa korisnikom "
											+ senderUsername + "?");
									final Optional<ButtonType> result = alert.showAndWait();
									if (result.get() == ButtonType.OK) {
										userComboBox.setValue("");
										new Thread(() -> {
											System.out.println("Non-UI thread");
											out.println("VALID CONNECTION:" + receiverInfo + ":" + senderInfo);
											userMessagesMap.put(senderInfo, "");
											readyMap.put(senderUsername, true);
										}).start();
										locationComboBox.setValue(senderId);
										userComboBox.setValue(senderUsername);
										/*userComboBox.setOnInputMethodTextChanged(e->{
											messageTextArea.setText("");
											return;
										}
										);*/
										
									}
								});

							} else if (msg.startsWith("VALID CONNECTION")) {
								if (!userMessagesMap.containsKey(senderInfo)) {
									userMessagesMap.put(senderInfo, "");
									/*Platform.runLater(() -> {
										locationComboBox.setValue(senderId);
										userComboBox.setValue(senderUsername);
									});*/
								}
								readyMap.put(senderUsername, true);
							}
							else if (msg.startsWith("MESSAGE")) {
								String newMessage = senderUsername + ": " + msg.split(":")[3] + "\n";
								final String messages = userMessagesMap.get(senderInfo) + newMessage;
								userMessagesMap.replace(senderInfo, messages);
								Platform.runLater(() -> {
									messageTextArea.setText(userMessagesMap.get(senderInfo));
								});
							} else if (msg.startsWith("FILE")) {
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
									String filePath = downloadFolder + File.separator + receiverUsername;
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
										e.printStackTrace();
									}
								}
							}

						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/*@FXML
	private void checkIfUserComboBoxIsNotEmpty() {
		new Thread(()->{
			while(true) {
			if(userComboBox.getValue()!="") {
				synchronized (userComboBoxLocker) {
					userComboBoxLocker.notifyAll();
				}
			}
			}
		}).start();
	}

	@FXML
	public void selectUser(ActionEvent e) {
		if(e==null) {
			messageTextArea.setText("");
			//});
			return;
		}
	new Thread(()->{
		//Platform.runLater(()->{
			if("".equals(userComboBox.getValue())) {
				synchronized(userComboBoxLocker) {
					try {
						userComboBoxLocker.wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					boolean ready=readyMap.get(userComboBox.getValue());
					if (ready) {
						//Platform.runLater(() -> {
							messageTextArea.setText(userMessagesMap.get(userComboBox.getValue()));
						//});
						return;
					}
					else {
						//Platform.runLater(() -> {
							messageTextArea.setText("");
						//});
						new Thread(() -> ChatClientSocket.connect(id, username, locationComboBox.getValue(), userComboBox.getValue()))
							.start();
						return;
					}
				}
			}
			/*if("".equals(userComboBox.getValue())) {
				return;
			}
			
			else {
				System.out.println(userComboBox.getValue());
			boolean ready=readyMap.get(userComboBox.getValue());
			if (ready) {
				//Platform.runLater(() -> {
				messageTextArea.setText(userMessagesMap.get(userComboBox.getValue()));
			//});
				return;
			}
			else {
				//Platform.runLater(() -> {
					messageTextArea.setText("");
				//});
				//new Thread(() ->
				ChatClientSocket.connect(id, username, locationComboBox.getValue(), userComboBox.getValue());
					//.start();
				return;
			}
			}*/
		//});
		//}).start();
//}
	/*private void actionUserComboBoxListener() {
		new Thread(()->{
			while(true) {
				if(isChatRequestProcedeed) {
					synchronized(userComboBoxLocker) {
						try {
							userComboBoxLocker.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
				}
			if(!previousUserComboBoxValue.equals(userComboBox.getValue()))
				isNewConnectionInitiated=true;
			while(isNewConnectionInitiated) {
			if(!"".equals(userComboBox.getValue())) {
				previousUserComboBoxValue=userComboBox.getValue();
					boolean ready=readyMap.get(userComboBox.getValue());
					if (ready) {
						//Platform.runLater(() -> {
						messageTextArea.setText(userMessagesMap.get(userComboBox.getValue()));
					//});
					}
					else {
						//Platform.runLater(() -> {
							messageTextArea.setText("");
						//});
						//new Thread(() ->
						ChatClientSocket.connect(id, username, locationComboBox.getValue(), userComboBox.getValue());
							isNewConnectionInitiated=false;
				}
				
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			}
		}).start();
	}*/
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
						e1.printStackTrace();
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/notification.fxml"));
		Parent root = null;
		NotificationController notificationController = new NotificationController(viewTimeImage.getScene(), id,username);
		loader.setController(notificationController);
		try {
			root = loader.load();

		} catch (IOException e1) {
			e1.printStackTrace();
			// Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
			// Logger.getLogger(FXMain.class.getName()).log(Level.WARNING,
			// e.fillInStackTrace().toString());
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/view/style.css");
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle(id);
		stage.show();
	}
	@FXML
	private void sendReport(MouseEvent e) {
		String PATH = "resources";
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(addFileImageView.getScene().getWindow());
		if (file != null && file.getName().endsWith(".pdf")) {
			System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

				try {
					AZSMDPInterface azsmdpServer=(AZSMDPInterface) Naming.lookup("rmi://127.0.0.1:1099/AZSMDPServer");
					try {
						azsmdpServer.uploadReport(new Report(
								file.getName(),
								Files.readAllBytes(file.toPath()),
								username,
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")),
								file.length()
						));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (RemoteException | NotBoundException | MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//AZSMDPInterface onlineShop = (AZSMDPInterface)
				
	}
	}
	class Wrapper {
		public boolean value;
	}
}
