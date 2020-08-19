package com.hyr.lean.rabbitmq.fanout;

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

/***
 * 启动了两个随机名称的消费者，它们Queue的名称不同
 * 启动生产者，发送一条消息，这时候可以发现两个接受端都收到了消息，这就是订阅者模式
 * @author mlj
 *
 */
public class FanoutReceiver {

	private static  final String EXCHANGE_NAME = "exchange_fanout_logs";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		
		Connection connection = factory.newConnection();
		
		Channel channel = connection.createChannel();
		
		//声明一个交换器
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		
		//获取队列名称,采用该种方式会生成一个随机名字的消息队列,并且在断开连接时会自动删除,但是不会影响订阅者模式,因为该场景下素有绑定的queue都会受到消息
		String queueName = channel.queueDeclare().getQueue();
		String routingKey  = "";
		//通过queueBind方法将新建的queue和exchange绑定,因为fanout模式不需要指定routing key的值
		channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
		
        System.out.println("FanoutReceiver [*] Waiting for messages. To exit press CTRL+C");
        
        
        Consumer consumer = new DefaultConsumer(channel){
        	@Override
        	public void handleDelivery(String consumerTag,
        	                               Envelope envelope,
        	                               AMQP.BasicProperties properties,
        	                               byte[] body) throws UnsupportedEncodingException {
        		String message = new String(body,"UTF-8");
                System.out.println("FanoutReceiver [x] Received '" + message + "'");
        	}
        };
        
        channel.basicConsume(queueName, true, consumer);
	}
}
