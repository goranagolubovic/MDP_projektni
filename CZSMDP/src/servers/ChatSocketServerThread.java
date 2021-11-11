package servers;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class ChatSocketServerThread extends Thread {

	private SSLSocket sock;
	private BufferedReader in;
	private PrintWriter out;
	private static boolean isSessionTerminated=false;
	private static Map<String, SSLSocket> clients = new HashMap<String, SSLSocket> ();
	
	public ChatSocketServerThread(SSLSocket sock) {
		this.sock = sock;
		try {
			// inicijalizuj ulazni stream
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			// inicijalizuj izlazni stream
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void forwardMessageToUser(String msg,SSLSocket sock) {
		 BufferedReader in;
		PrintWriter out;
		try {
			
			out = new PrintWriter(
			         new BufferedWriter(new OutputStreamWriter(
			                         sock.getOutputStream())), true);
			out.println(msg);
			//in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			//System.out.println("Klijent dobija:"+in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		System.out.println(msg);
		
			
	}

	@Override
	public void run() {
		try {
			
			String request = in.readLine();
			//String[] params = request.split(ProtocolMessages.MESSAGE_SEPARATOR.getMessage());
			if (request.startsWith("CHAT")) {
				System.out.println("Uspostavljen chat izmedju korisnika");
				String [] requestParams=request.split(":");
//				String[] socketString=requestParams[2].substring(6).split(",");
//				SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
//				String host=socketString[0].split("/")[1];
//				int port=Integer.valueOf(socketString[1].split("=")[1]);
//				String localPortString=socketString[2].split("=")[1];
//				int localport=Integer.valueOf(localPortString.substring(0, localPortString.length()-1));
//				System.out.println(localport);
//				SSLSocket socket=(SSLSocket) sf.createSocket(socketString[0].split("/")[1],Integer.valueOf(socketString[1].split("=")[1]),InetAddress.getByName("localhost")
//						,localport);
				clients.put((String)requestParams[1],sock);
				 
			}
			else if(request.startsWith("END")) {
				in.close();
				sock.close();
				out.close();
			}
			else {
				String[] requestString=request.split(":");
				javax.net.ssl.SSLSocket sock=clients.get(requestString[0]);
	        	// BufferedReader in = new BufferedReader(new InputStreamReader(
		                  //  sock.getInputStream()));
		            // inicijalizuj izlazni stream
		          //  PrintWriter out = new PrintWriter(
		               //     new BufferedWriter(new OutputStreamWriter(
		                    //                sock.getOutputStream())), true);
				
						 forwardMessageToUser(requestString[1],sock);
		           
				//System.out.println(request);
			}
//			in.close();
//			sock.close();
//			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}

}

