package servers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import controller.CZSMDPController;
import main.FXMain;
import properties.ConfigProperties;

public class ChatSocketServer {
	public static boolean work = true;
	public static ServerSocket ss;
	private static ConfigProperties configProperties=new ConfigProperties();
	private static FileHandler handler;
	
	public static void main(String[] args) {
		try {
			// slusaj zahteve na datom portu
			System.setProperty("javax.net.ssl.keyStore", configProperties.getProperties().getProperty("KEY_STORE_PATH"));
			System.setProperty("javax.net.ssl.keyStorePassword",configProperties.getProperties().getProperty("KEY_STORE_PASSWORD"));

			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			ServerSocket ss = ssf.createServerSocket(Integer.valueOf(configProperties.getProperties().getProperty("AUTH_SERVER_PORT")));
			System.out.println("Chat Server running...");
		
			while (work) {
				// prihvataj klijente
				SSLSocket s = (SSLSocket) ss.accept();
				// startuj nit za svakog klijenta
				new ChatSocketServerThread(s).start();
			}
		} catch (Exception ex) {
			 Logger.getLogger(ChatSocketServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

}
