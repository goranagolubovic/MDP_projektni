package servers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.net.ssl.SSLSocket;

import com.google.gson.Gson;

import controller.CZSMDPController;
import main.FXMain;
import properties.ConfigProperties;
import redis.clients.jedis.Jedis;

public class ChatSocketServerThread extends Thread {

	private SSLSocket sock;
	private BufferedReader in;
	private PrintWriter out;
	private static Map<String, SSLSocket> clients = new HashMap<String, SSLSocket>();
	private Gson gson=new Gson();
	private Jedis jedis=new Jedis("localhost",8081);
	private static ConfigProperties configProperties=new ConfigProperties();
	public ChatSocketServerThread(SSLSocket sock) {
		//Logger.getLogger(ChatSocketServerThread.class.getName()).addHandler(FXMain.handler);
		this.sock = sock;
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
		} catch (Exception ex) {
			Logger.getLogger(ChatSocketServerThread.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String request="";
				try {
				request = in.readLine();
				}
				catch(Exception e) {
					return;
				}
				String senderInfo = request.split(":")[1];
				String senderUsername = senderInfo.split("#")[1];
				System.out.println(request);
				if (request.startsWith("EXIT")) {
					in.close();
					out.close();
					sock.close();
					clients.remove(senderInfo);
					Logger.getLogger(ChatSocketServerThread.class.getName()).log(Level.INFO,
							"User " + senderUsername + " disconnected..."
					);
					return;
				}

				if (request.startsWith("ADD SOCKET")) {
					if (clients.containsKey(senderInfo)) {
						clients.replace(senderInfo, sock);
					} else {
						clients.put(senderInfo, sock);
					}
					System.out.println("User " + senderUsername + " conected...");
					continue;
				}
				if(request.startsWith("SEND ONLINE USERS")) {
					Set<String> clientsInfo=clients.keySet();
					String station=request.split(":")[2];
					List<String>onlineUsersInStation=clientsInfo.stream()
								.filter(elem->elem.startsWith(station))
								.map(el->el.split("#")[1])
								.collect(Collectors.toList());
					SSLSocket userSocket=clients.get(senderInfo);
					PrintWriter pw = new PrintWriter(userSocket.getOutputStream(), true);
					String usersOnline="";
					for(String u:onlineUsersInStation) {
						usersOnline+=u+";";
					}
					if(usersOnline.length()!=0) {
					pw.println("ONLINE USERS:"+usersOnline.substring(0,usersOnline.length()-1)+":"+clientsInfo.size());
					}
					else {
						pw.println("ONLINE USERS:"+"empty");
					}
					continue;
				}

				String receiverInfo = request.split(":")[2];
				SSLSocket receiverSocket = clients.get(receiverInfo);
				if(receiverSocket!=null) {
				PrintWriter pw = new PrintWriter(receiverSocket.getOutputStream(), true);
				pw.println(request);
				}
				else {
					SSLSocket senderSocket = clients.get(senderInfo);
					PrintWriter pw = new PrintWriter(senderSocket.getOutputStream(), true);
					pw.println("NOT CONNECTED:"+receiverInfo.split("#")[1]);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(ChatSocketServerThread.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}

	}
}
