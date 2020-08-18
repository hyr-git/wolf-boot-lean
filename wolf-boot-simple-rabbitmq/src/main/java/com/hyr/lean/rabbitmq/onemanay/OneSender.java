package com.hyr.lean.rabbitmq.onemanay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.AMQP.BasicProperties;

public class OneSender {
	
	//声明队列
	private static final String QUEUE_NAME="task_queue";
	
	 private static String getMessage(String[] strings) {
	        if (strings.length < 1)
	            return "Hello World!";
	        return joinStrings(strings, " ");
	 }

	 private static String joinStrings(String[] strings, String delimiter) {
	        int length = strings.length;
	        if (length == 0) return "";
	        StringBuilder words = new StringBuilder(strings[0]);
	        for (int i = 1; i < length; i++) {
	            words.append(delimiter).append(strings[i]);
	        }
	        return words.toString();
	    }
	 
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		//设置端口
		factory.setPort(5672);
        //设置密码用户名
		factory.setUsername("guest");
		factory.setPassword("guest");
        //设置虚拟机,每个虚拟机相当于一个小的mq
		factory.setVirtualHost("/");
		
		//建立连接
		Connection connection = factory.newConnection();
		
		//建立通道,生产者和消费者都是在通道中完成
		Channel channel = connection.createChannel();
		
		//是否持久化---表示该队列是否持久化到磁盘(若要是队列消息不丢失,同时需要将消息声明为持久化)
		boolean durable = true;
		
		//队列是否为链接独占,链接关闭后队列即被删除
		boolean exclusive = false;
		//没有消费者订阅该队列,队列被删除
		boolean autoDelete = false;
		
		//可选参数,可以指定队列长度,消息生存时间、镜像设置等。
		Map argsMap = new HashMap();
		
		/*
		 * 声明一个队列名称为task_queue
		 */
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, argsMap);
		
		String[] strings= {"hello ","demo ","demo 001","demo 002"};
		
		String message = getMessage(strings);
		
		//交换机---若不指定使用将使用mq的默认交换器,设为为""
		String exchange = "";
		//路由key--交换计根据路由key来将消息发送到指定的队列,若使用默认的交换器,routingkey设置为队列的名称
		String routingKey = QUEUE_NAME;
		//消息的属性
		BasicProperties props = MessageProperties.PERSISTENT_TEXT_PLAIN;
		//消息内容
		byte[] body = message.getBytes("UTF-8");
		
		channel.basicPublish(exchange, routingKey, props, body);
		
		channel.basicPublish(exchange, routingKey, props, body);

		channel.basicPublish(exchange, routingKey, props, body);
		
		channel.basicPublish(exchange, routingKey, props, body);

	    System.out.println(" [x] Sent '" + message + "'");
		
	    channel.close();
        connection.close();
		
		
	}
}
