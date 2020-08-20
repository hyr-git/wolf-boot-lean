package com.hyr.lean.rabbitmq.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class FanoutSender {
	
	private static final String EXCHANGE_NAME = "exchange_fanout_logs";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = null;
		Connection connection = null;
		Channel channel = null;
		try {
			factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			//添加了Exchange的声明,并且采用的是fanout类型
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
			String atr[] = {"test1","check2","demo1","dfdfdf"};
			String message = getMessage(atr);
			
			String routingKey = "";
			//声明了Exchange的名称,而不是像之前那样给了个空值
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
	        System.out.println("FanoutSender [x] Sent '" + message + "'");

		} finally {
			channel.close();
			connection.close();
		}
	}
	
	private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "info: Hello World!";
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
}
