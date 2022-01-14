package servers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;

public class ChatSocketServerThread extends Thread {

	private SSLSocket sock;
	private BufferedReader in;
	private PrintWriter out;
	private static Map<String, SSLSocket> clients = new HashMap<String, SSLSocket>();

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

				String receiverInfo = request.split(":")[2];
				SSLSocket userSocket = clients.get(receiverInfo);
				PrintWriter pw = new PrintWriter(userSocket.getOutputStream(), true);
				pw.println(request);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
