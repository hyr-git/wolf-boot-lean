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

/***
 * https://www.cnblogs.com/bigdataZJ/p/rabbitmq3.html
 * @author mlj
 *
 */
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
		
		/*
		 * 没有basiQos设置，会执行轮询策略，每个消费者都消费了两个消息
		 * basicQos设置,会执行公平机制。
		 * 即,第一个消息给C1，第二个给C2,第三个消息来的时候,发现C2还在消费,就派发给了已经消费玩空闲的C1，第四个消息来时,发现C2仍然在消费，
		 * 这时候就把消息发送给了消费完第三个消息的C1,C1总共消费了3条消息用时6秒，而C2消费一条消息耗时8秒,所以这就是公平机制。
		 * 
		 * 使用了basicQos的公平机制更加合理,能够很好的做到负载均衡,避免因为不顾消费者的消费情况而盲目派发情况的出现.
		 */
		channel.basicQos(1);
		
		//是否持久化---表示该队列是否持久化到磁盘(若要是队列消息不丢失,同时需要将消息声明为持久化)
		boolean durable = true;
		
		//队列是否为链接独占,链接关闭后队列即被删除
		boolean exclusive = false;
		//没有消费者订阅该队列,队列被删除
		boolean autoDelete = false;
		
		//可选参数,可以指定队列长度,消息生存时间、镜像设置等。
		Map<String, Object> argsMap = new HashMap<String, Object>();
		
		/*
		 * 声明一个队列名称为task_queue  --- 队列持久化
		 */
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, argsMap);
		
		String[] strings= {"hello ","demo ","demo 001","demo 002"};
		
		String message = getMessage(strings);
		
		//交换机---若不指定使用将使用mq的默认交换器,设为为""
		String exchange = "";
		//路由key--交换计根据路由key来将消息发送到指定的队列,若使用默认的交换器,routingkey设置为队列的名称
		String routingKey = QUEUE_NAME;
		
		/*
		 * 消息的属性----消息持久化
		 * deliveryMode=1代表不持久化，deliveryMode=2代表持久化
		 * Content-type "text/plain", deliveryMode 2 (persistent), priority zero 
		 */
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
