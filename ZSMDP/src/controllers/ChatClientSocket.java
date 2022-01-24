package controllers;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import main.FXMain;
import properties.ConfigProperties;


public class ChatClientSocket {

		public static BufferedReader in;
		public static PrintWriter out;
		
		private String clientUsername;
		private static SSLSocket sock;
		private static ConfigProperties configProperties=new ConfigProperties();
		private static Logger log = Logger.getLogger(ChatClientSocket.class.getName());
		public ChatClientSocket(String username) {
			System.setProperty("javax.net.ssl.trustStore", configProperties.getProperties().getProperty("KEY_STORE_PATH"));
			System.setProperty("javax.net.ssl.trustStorePassword",  configProperties.getProperties().getProperty("KEY_STORE_PASSWORD"));
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			clientUsername = username;
			try {
				sock = (SSLSocket) sf.createSocket( configProperties.getProperties().getProperty("SERVER_IP_ADDRESS"), Integer.valueOf(configProperties.getProperties().getProperty("AUTH_SERVER_PORT")));
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(), true);
			} catch (IOException e) {
				Logger.getLogger(ChatClientSocket.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
		
	    public static boolean connect(String id1, String username1, String id2, String username2) {
	        try {
	            System.setProperty("javax.net.ssl.trustStore", configProperties.getProperties().getProperty("KEY_STORE_PATH"));
	    		System.setProperty("javax.net.ssl.trustStorePassword", configProperties.getProperties().getProperty("KEY_STORE_PASSWORD"));

	    		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
	    		SSLSocket sock = (SSLSocket) sf.createSocket(configProperties.getProperties().getProperty("SERVER_IP_ADDRESS"), Integer.valueOf(configProperties.getProperties().getProperty("AUTH_SERVER_PORT")));
	      
	            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
	           
				out.println("CHAT:" + (id1 + "#" + username1) + ":" + (id2 + "#" + username2));
	            return true;
	        } catch (Exception ex) {
	        	Logger.getLogger(ChatClientSocket.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
	        }
	        return false;
	    }

		public void addSocketInMap(String id) {
			out.println("ADD SOCKET:"+(id+"#"+clientUsername));
		}
	    public static void sendMessage(String msg,String username,boolean isMessageFile) {
	        try {
	        	SSLSocket sock=null;
	        	 PrintWriter out = new PrintWriter(
		                    new BufferedWriter(new OutputStreamWriter(
		                                    sock.getOutputStream())), true);
	        	 if(!isMessageFile) {
	        		 out.println(username+";"+msg);
	        	 }
	        	 else {
	        		 File f=new File(msg);
	        		 FileInputStream fis=new FileInputStream(f);
	        		 byte[] fileNameBytes=f.getName().getBytes();
	        		 byte[] fileContentBytes=new byte[(int)f.length()];
	        		 fis.read(fileContentBytes);
	        		 out.write(username+";"+fileNameBytes.length);
	        		 out.write(username+";"+fileNameBytes);
	        		 out.write(username+";"+fileContentBytes.length);
	        		 out.write(username+";"+fileContentBytes);
	        	 }
	        } catch (Exception ex) {
	        	Logger.getLogger(ChatClientSocket.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
	        }
	    }
}
