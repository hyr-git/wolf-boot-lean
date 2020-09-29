package com.hyr.lean.rabbitmq.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


/*****
 * Direct模式的绑定
new Binding(someQueue, someDirectExchange, "foo.bar"）
Fanout模式的绑定
new Binding(someQueue, someFanoutExchange)
Topic模式的绑定
new Binding(someQueue, someTopicExchange, "foo.*")
 * @author mlj
 *
 */
public class DirectReceiverEmitLog {

	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = null;
		Connection connection = null;
		Channel channel = null;
		String queueName = "";
		try {

			factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();
			channel = connection.createChannel();

			// 声明使用的exchange类型为DIRECT
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

			queueName = channel.queueDeclare().getQueue();

			/*
			 * 这里两个参数info和error表示绑定了两个routing key. 即如果发送routing
			 * key为info的消息该队列能接受到,若发送routing key为error，该队列也能收到
			 */

			String argv[] = { "info", "error" };
			if (argv.length < 1) {
				System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
				System.exit(1);
			}
			for (String severity : argv) {
				channel.queueBind(queueName, EXCHANGE_NAME, severity);
			}
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
				}
			};

			channel.basicConsume(queueName, true, consumer);

		} finally {
			// TODO: handle finally clause
		}
	}
}
