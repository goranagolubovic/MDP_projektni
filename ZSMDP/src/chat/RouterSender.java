package chat;



import java.util.Random;
import java.util.Scanner;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RouterSender {

	private final static String EXCHANGE_NAME = "mdp_direct";
	private final static String [] TAGS = {"test", "temp", "util"};
	
	public static void startSending() throws Exception {
		Connection connection = ConnectionFactoryUtil.createConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		Scanner in = new Scanner(System.in);
		System.out.print("Unesi prvu poruku: ");
		String message;
		while (!(message = in.nextLine()).equals("QUIT")) {
			String tag = TAGS[new Random().nextInt(TAGS.length)];
			
			channel.basicPublish(EXCHANGE_NAME, tag, null, message.getBytes("UTF-8"));
			
			System.out.println("Sending: '" + message + "'" + " tag " + tag);
			System.out.print("Unesi sljedecu poruku: ");
		}
		in.close();
		channel.close();
		connection.close();
	}
}