package com.hyr.lean.rabbitmq.direct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/****
 * 一个生产者P、一个交换机EX、对个消息队列Q、多个消费者C
 * 在Exchange和Queue中,我们看到不同的规则,也就是Routing Key
 * @author mlj
 *
 * 业务场景:
 *    log日志根据级别派发消息的例子,log系统氛围error、info、warn、debug等。
 * 该模型首先实现了定向派发，而不再是订阅模式那种广播式的派发。同一条消息既可以派发给一个Queue，也可以同时派发给两个或者多个Queue，这就是该模式的灵活之处。下面来看看实例
 */
public class DirectSenderEmitLog {
	 private static final String EXCHANGE_NAME = "direct_logs";
	public static void main(String[] args) throws IOException, TimeoutException {
         ConnectionFactory factory = null;
         Connection connection = null;
         Channel channel = null;
         try {
        	 factory = new ConnectionFactory();
        	 connection = factory.newConnection();
        	 channel = connection.createChannel();
        	 
        	 
        	// String queue = channel.queueDeclare().getQueue();
        	 
        	 /*
        	  * durable-标识队列持久化
        	  * exclusive--标志排他处理,
        	  * autoDelete--消息发送出去后直接从内存中删除
        	  * arguments--标识队列中的属性配置,例如超时时间
        	 
        	 Boolean durable = true;
        	 boolean exclusive = false;
        	 boolean autoDelete = false;
        	 Map<String,Object> arguments = new HashMap<String,Object>();
        	 channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
        	  */
        	 //第一个参数是要绑定key的名称，第二个参数是要发送的消息内容
        	// String argv[] = {"info","hello world logs"};
        	 String  argv[] = {"error","hello world logs"};
			 
        	 channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        	 
        	 //channel.queueBind(queue, EXCHANGE_NAME, EXCHANGE_NAME);
        	 
        	 //通过程序参数赋值给Routing Key，作为发送消息的规则
        	 String routingKey = getSeverity(argv);
        	 //通过程序参数赋值作为消息实体发送到Queue
             String message = getMessage(argv);

             channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
		     System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
         
         } finally {
        	 if(null != channel) {
        		 channel.close();
        	 }
        	 if(null != connection) {
        		 connection.close();
        	 }
        }
	}
	
	
	 private static String getSeverity(String[] strings){
	        if (strings.length < 1)
	            return "info";
	        return strings[0];
	    }

	    private static String getMessage(String[] strings){
	        if (strings.length < 2)
	            return "Hello World!";
	        return joinStrings(strings, " ", 1);
	    }

	    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
	        int length = strings.length;
	        if (length == 0 ) return "";
	        if (length < startIndex ) return "";
	        StringBuilder words = new StringBuilder(strings[startIndex]);
	        for (int i = startIndex + 1; i < length; i++) {
	            words.append(delimiter).append(strings[i]);
	        }
	        return words.toString();
	    }
}
