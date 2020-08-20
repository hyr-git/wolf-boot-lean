package com.hyr.lean.rabbitmq.receiver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver {
    private final static String QUEUE_NAME = "hello.august";
    private final static String EXCHANEG_NAME = "direct_exchange";


	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		boolean durable = false;
		boolean exclusive = false;
		boolean autoDelete = false;
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);
		channel.exchangeDeclare(EXCHANEG_NAME, BuiltinExchangeType.DIRECT,true);

		channel.queueBind(QUEUE_NAME, EXCHANEG_NAME, QUEUE_NAME);
		
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws UnsupportedEncodingException {
				String message = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + message + "'");
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true,consumer);
	}
}
