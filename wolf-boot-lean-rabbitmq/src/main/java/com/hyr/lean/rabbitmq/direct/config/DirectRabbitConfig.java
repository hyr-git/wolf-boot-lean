package com.hyr.lean.rabbitmq.direct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class DirectRabbitConfig {

	public final static String queueName = "direct";
	
	@Bean
	public Queue directQueue() {
		return new Queue(queueName);
	}
	
	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}
}
