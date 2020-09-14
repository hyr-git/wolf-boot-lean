package com.hyr.lean.rabbitmq.message.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class UserDirectRabbitConfig {

	public final static String queueName = "directUser";
	
	@Bean
	public Queue directUserQueue() {
		return new Queue(queueName);
	}
	
}
