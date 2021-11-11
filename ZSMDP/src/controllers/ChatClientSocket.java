package controllers;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class ChatClientSocket {
	
		private static Map<String,SSLSocket> chatClientSockets=new HashMap<>();
		private static BufferedReader in;
		public BufferedReader getIn() {
			return in;
		}

		public void setIn(BufferedReader in) {
			this.in = in;
		}

		private static PrintWriter out;
		private String clientUsername;
		private static SSLSocket sock;
		
	    private static final int INFO_SERVER_PORT = 9000;
	    private static final int AUTH_SERVER_PORT = 8443;
	    private static final String IP_ADDRESS = "127.0.0.1";
	    private static final String KEY_STORE_PATH = "./keystore.jks";
		private static final String KEY_STORE_PASSWORD = "securemdp";
		
		
		public ChatClientSocket(String username) {
			
			 System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
	    		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
   		 // otvori socket prema drugom racunaru
   		    try {
				this.sock = (SSLSocket) sf.createSocket(IP_ADDRESS, AUTH_SERVER_PORT);
				  in = new BufferedReader(new InputStreamReader(
		                    sock.getInputStream()));
		            // inicijalizuj izlazni stream
		           out = new PrintWriter(
		                    new BufferedWriter(new OutputStreamWriter(
		                                    sock.getOutputStream())), true);
		           //this.clientUsername=in.readLine();
		           chatClientSockets.put(username,sock);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    public static boolean connect(String username1, String username2) {
	        try {
	            // odredi adresu racunara sa kojim se povezujemo
	            // (povezujemo se sa nasim racunarom)
	        	
	            /*InetAddress addr = InetAddress.getByName(IP_ADDRESS);
	            System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
	    		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);

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
	            String requestMessage ="CHAT:" + username1 +":" + username2+":"+sock;
	            out.println(requestMessage);
	            String response = in.readLine();
	            return true;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }
	    public static void endSession() {
	    	out.println("END");
	    }

	    public static void sendMessage(String msg,String username) {
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
	        	SSLSocket sock=chatClientSockets.get(username);
	        	 PrintWriter out = new PrintWriter(
		                    new BufferedWriter(new OutputStreamWriter(
		                                    sock.getOutputStream())), true);
	            out.println(username+":"+msg);
	            //String response = in.readLine();
	           // System.out.println("Poruka "+response);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

		

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
