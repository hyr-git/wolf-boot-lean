package com.hyr.lean.rabbitmq.onemanay;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ManayReceiver {

	private static final String QUEUE_NAME="task_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		boolean durable = true;
		boolean exclusive = false;
		boolean autoDelete = false;
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
        channel.basicQos(1);
        
        
        final Consumer consumer = new DefaultConsumer(channel) {
        	@Override
        	public void handleDelivery(String consumerTag,Envelope envolope,AMQP.BasicProperties properties,byte[] body) throws IOException{
        		String msgmessage = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + msgmessage + "'");
                try {
                	doWork(msgmessage);
				} finally {
                    System.out.println(" [x] Done");
				    channel.basicAck(envolope.getDeliveryTag(), false);
				}
        	}
        };
        channel.basicConsume(QUEUE_NAME, consumer);
	}
	
	
	private static void doWork(String task) {
		for (char ch : task.toCharArray()) {
			if(ch =='.'){
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
                     Thread.currentThread().interrupt();
				}
			}
			
		}
	}
}
