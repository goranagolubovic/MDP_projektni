package controllers;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class ChatClientSocket {

		public static BufferedReader in;
		public static PrintWriter out;
		
		private String clientUsername;
		private static SSLSocket sock;
		
	    private static final int INFO_SERVER_PORT = 9000;
	    private static final int AUTH_SERVER_PORT = 8443;
	    private static final String SERVER_IP_ADDRESS = "127.0.0.1";
	    private static final String KEY_STORE_PATH = "./keystore.jks";
		private static final String KEY_STORE_PASSWORD = "securemdp";
		
		
		public ChatClientSocket(String username) {
			System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
			System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			clientUsername = username;

			try {
				sock = (SSLSocket) sf.createSocket(SERVER_IP_ADDRESS, AUTH_SERVER_PORT);
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	    public static boolean connect(String id1, String username1, String id2, String username2) {
	        try {
	            System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
	    		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);

	    		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
	    		SSLSocket sock = (SSLSocket) sf.createSocket(SERVER_IP_ADDRESS, AUTH_SERVER_PORT);
	      
	            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
	           
				out.println("CHAT:" + (id1 + "#" + username1) + ":" + (id2 + "#" + username2));
	            return true;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }

		public void addSocketInMap(String id) {
			out.println("ADD SOCKET:"+(id+"#"+clientUsername));
		}
	    public static void endSession() {
	    	out.println("END");
	    }

	    public static void sendMessage(String msg,String username,boolean isMessageFile) {
	        try {
	        	/*
	            // (povezujemo se sa nasim racunarom)
	        	SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
	    		 // otvori socket prema drugom racunaru
	    		SSLSocket sock = (SSLSocket) sf.createSocket(IP_ADDRESS, AUTH_SERVER_PORT);
	            // inicijalizuj ulazni stream
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    sock.getInputStream()));
	            // inicijalizuj izlazni stream
	            PrintWriter out = new PrintWriter(
	                    new BufferedWriter(new OutputStreamWriter(
	                                    sock.getOutputStream())), true);
	                                    
			*/
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
	        		 //out.println(username+":"+fileContentBytes);
	        	 }
	            //String response = in.readLine();
	           // System.out.println("Poruka "+response);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

		/*public void connectionRefused(String chatInitiator, String user) {
			out.println("INVALID CONNECTION:"+chatInitiator+":"+user);
		}*/

		

	   /* public static void main(String[] args) {

	        try {
	            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	            System.out.println("Unesite korisnicko ime:");
	            String username = keyboard.readLine();
	            System.out.println("Unesite lozinku:");
	            String password = keyboard.readLine();
	            System.out.println("Unesite grad:");
	            String town = keyboard.readLine();

	            if (connect(username, password)) {
	                getInfo(town);
	            } else {
	                System.out.println("Pristup nije dozvoljen");
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }*/
}
