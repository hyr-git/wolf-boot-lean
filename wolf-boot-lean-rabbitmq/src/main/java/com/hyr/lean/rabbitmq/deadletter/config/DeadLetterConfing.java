package com.hyr.lean.rabbitmq.deadletter.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;

/**
 * 生产者 --> 消息 --> 交换机 --> 队列 --> 变成死信 --> DLX交换机 -->队列 --> 消费者
 * @author mlj
 *
 */
public class DeadLetterConfing {

	public static final String DIRECT_EXCHANGE_NAME = "DL_EXCHANGE";
	
	public static final String REDIRECT_ROUTING_KEY = "KEY_R";
	
	public static final String DEAD_LETTER_ROUTING_KEY = "DL_KEY";
	
	public static final String DL_QUEUE = "DL_QUEUE";
	
	public static final String REDIRECT_QUEUE = "REDIRECT_QUEUE";
	
	/**
	 * 私信交换器
	 * @return
	 */
	@Bean("deadLetterExchange")
	public Exchange deadLetterExchange() {
		return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
	}
	
	/**
	 * 声明了一个deadLetter的私信队列，该队列配置了一些属性,
	 * x-dead-letter-exchange表明私信交换器，x-dead-letter-routing-key表明私信路由键，因为是direct模式，所以需要设置这个路由键。
	 * @return
	 */
	@Bean("deadLetterQueue")
	public Queue deadLetterQueue() {
		Map<String,Object> argsMap = new HashMap<String,Object>();
		//指定私信交换机
		argsMap.put("x-dead-letter-exchange", DIRECT_EXCHANGE_NAME);
		//指定私信路邮件
		argsMap.put("x-dead-letter-routing-key", REDIRECT_ROUTING_KEY);

		return QueueBuilder.durable(DL_QUEUE).withArguments(argsMap).build();
	}
	
	/***
	 * 声明了一个替补队列redirectQueue，变成私信的消息最终就是存在这个队列的。
	 * @return
	 */
	@Bean("redirectQueue")
	public Queue redirectQueue() {
		return QueueBuilder.durable(REDIRECT_QUEUE).build();
	}
	
	/***
	 * 私信路由通过DL_KEY绑定到私信队列上
	 * @return
	 */
	@Bean
	public Binding deadLetterBinding() {
		return new Binding(DL_QUEUE,Binding.DestinationType.QUEUE,DIRECT_EXCHANGE_NAME,DEAD_LETTER_ROUTING_KEY,null);
	}
	
	/***
	 * 私信路由头通过KEY_R绑定键绑定到私信队列上。
	 * @return
	 */
	public Binding redirectBinding(){
		return new Binding(REDIRECT_QUEUE,Binding.DestinationType.QUEUE,DIRECT_EXCHANGE_NAME,REDIRECT_ROUTING_KEY,null);
	}
}
