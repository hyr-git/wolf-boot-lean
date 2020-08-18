package com.hyr.lean.rabbitmq.sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

    private final static String QUEUE_NAME = "hello.august";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFacctory = new ConnectionFactory();
		connectionFacctory.setHost("localhost");
		
		Connection connection = connectionFacctory.newConnection();
		Channel channel = connection.createChannel();
		
		boolean durable = false;
		boolean exclusive = false;
		boolean autoDelete = false;
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);
		
		String routingKey = QUEUE_NAME;
		String message = "hello work";
		channel.basicPublish("", routingKey, null, message.getBytes());
		System.out.println("send message:"+message);
		
		channel.close();
		connection.close();
	}
	
}
