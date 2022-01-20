package servers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.net.ssl.SSLSocket;

import com.google.gson.Gson;

import database.RedisDatabase;
import redis.clients.jedis.Jedis;

public class ChatSocketServerThread extends Thread {

	private SSLSocket sock;
	private BufferedReader in;
	private PrintWriter out;
	private static Map<String, SSLSocket> clients = new HashMap<String, SSLSocket>();
	private Gson gson=new Gson();
	private Jedis jedis=new Jedis("localhost",8081);
	public ChatSocketServerThread(SSLSocket sock) {
		this.sock = sock;
		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String request = in.readLine();
				if (request.equals("END")) {
					in.close();
					sock.close();
					out.close();
					System.out.println("User disconected...");
					continue;
				}
				System.out.println(request);

				String senderInfo = request.split(":")[1];
				String senderUsername = senderInfo.split("#")[1];

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
						usersOnline+=u+"\n";
					}
					if(usersOnline.length()!=0) {
					pw.println("ONLINE USERS:"+usersOnline.substring(0,usersOnline.length()-1));
					}
					else {
						pw.println("ONLINE USERS:"+"empty");
					}
					continue;
				}
				if(request.startsWith("EXIT")) {
					clients.remove(senderInfo);
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
					pw.println("NOT CONNECTED:"+senderInfo.split("#")[1]);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
