package controllers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NotificationController  implements Initializable{
	private String id;
	private Scene previousScene;
	private String username;
	@FXML
	TextField notificationTextField;
	private static final int PORT = 20000;
	private static final String HOST = "224.0.0.11";
	
	public  NotificationController(Scene previousScene,String id,String username) {
			this.id=id;
			this.previousScene=previousScene;
			this.username=username;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	@FXML
	private void sendNotification(MouseEvent e) {
		MulticastSocket socket = null;
		byte[] buf = new byte[6];
		try {
			socket = new MulticastSocket();
			InetAddress address = InetAddress.getByName(HOST);
			socket.joinGroup(address);
				String msg = username+":"+notificationTextField.getText();
				buf = msg.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
				socket.send(packet);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();
	}
}
