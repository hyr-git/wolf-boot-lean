package com.hyr.lean.rabbitmq.sender;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

    private final static String QUEUE_NAME = "hello.august";
    
    private final static String EXCHANEG_NAME = "direct_exchange";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFacctory = new ConnectionFactory();
		connectionFacctory.setHost("localhost");
		
		Connection connection = connectionFacctory.newConnection();
		Channel channel = connection.createChannel();
		
		boolean durable = false;
		boolean exclusive = false;
		boolean autoDelete = false;
		//声明一个queue队列
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);
		
		//声明一个交换器
		channel.exchangeDeclare(EXCHANEG_NAME, BuiltinExchangeType.DIRECT,true);
		
		String routingKey = QUEUE_NAME;
		String message = "hello work >>>>>>>>"+ UUID.randomUUID().toString();
		channel.basicPublish(EXCHANEG_NAME, routingKey, null, message.getBytes());
		System.out.println("send message:"+message);
		
		channel.close();
		connection.close();
	}
	
}
