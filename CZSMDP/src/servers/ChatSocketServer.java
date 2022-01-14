package servers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


public class ChatSocketServer {

	// konstante
	public static int TCP_PORT=9000;
	public static String AUTH_SERVER_IP="127.0.0.1";
	public static int AUTH_SERVER_PORT=8443;

	
	public static boolean work = true;
	public static ServerSocket ss;
	private static final String KEY_STORE_PATH = "./keystore.jks";
	private static final String KEY_STORE_PASSWORD = "securemdp";
	
	
	public static void main(String[] args) {
		try {
			// slusaj zahteve na datom portu
			System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
			System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);

			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			ServerSocket ss = ssf.createServerSocket(AUTH_SERVER_PORT);
			System.out.println("Chat Server running...");
		
			while (work) {
				// prihvataj klijente
				SSLSocket s = (SSLSocket) ss.accept();
				// startuj nit za svakog klijenta
				new ChatSocketServerThread(s).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
