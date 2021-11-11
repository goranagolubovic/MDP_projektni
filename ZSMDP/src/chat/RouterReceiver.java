package chat;

import java.io.IOException;
import java.util.Random;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RouterReceiver {

	private final static String EXCHANGE_NAME = "mdp_direct";
	private final static String[] TAGS = { "test", "temp", "util" };

	public static void startReceiving() throws Exception {
		String tag = TAGS[new Random().nextInt(TAGS.length)];
		System.out.println("TAG:" + tag);

		Connection connection = ConnectionFactoryUtil.createConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		String queueName = channel.queueDeclare().getQueue();

		channel.queueBind(queueName, EXCHANGE_NAME, tag);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("Message received: '" + message + "'");

			}
		};
		channel.basicConsume(queueName, true, consumer);

	}
}