package com.hyr.lean.rabbitmq.deadletter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/****
 * 死信队列设置
 * @author mlj
 *
 */
public class DLXProducter {

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = null;
		Connection connection = null;
		Channel channel = null;
		try {
			factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			//死信交换器名称
			String exchange = "test_dlx_exchange";
	        String routingKey = "dlx.save";
	        
	        String msg = "Hello RabbitMQ DLX Message";
	        
	        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
	                .deliveryMode(2)  //消息持久化
	                .contentEncoding("UTF-8")
	                .expiration("10000") //超时时间
	                .build();
	        //发送消息
	        channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
	        
	        channel.addConfirmListener(new ConfirmCallback() {
				
				@Override
				public void handle(long deliveryTag, boolean multiple) throws IOException {
					// TODO Auto-generated method stub
					
				}
			}, null);
		} finally {
			
		}
	}
}
