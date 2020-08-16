package com.hyr.lean.rabbitmq.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/***
 * Fanout --广播模式或者订阅模式。给反out转发器发送消息,绑定了这个转发器的所有队列都会受到该消息。
 * @author m1832
 *
 */
@Configuration
public class FanoutRabbitConfig {

	public static final String FANOUT_A_QUEUE_NAME = "fanout.A";
	public static final String FANOUT_B_QUEUE_NAME = "fanout.B";
	public static final String FANOUT_C_QUEUE_NAME = "fanout.C";
	
	public static String FANOUT_EXCHANGE = "fanout_exchange";
	
	
	
	//===============以下是验证Fanout Exchange的队列==========
	@Bean
	public Queue fanoutAQueue() {
		return new Queue(FANOUT_A_QUEUE_NAME);
	}
	
	@Bean
	public Queue fanoutBQueue() {
		return new Queue(FANOUT_B_QUEUE_NAME);
	}

	@Bean
	public Queue fanoutCQueue() {
		return new Queue(FANOUT_C_QUEUE_NAME);
	}

    //===============以上是验证Fanout Exchange的队列==========
	
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	
	@Bean
	public Binding bindingExchangeA(FanoutExchange fanoutExchange,Queue fanoutAQueue) {
		return BindingBuilder.bind(fanoutAQueue).to(fanoutExchange);
	}
	
	@Bean
	public Binding bindingExchangeB(FanoutExchange fanoutExchange,Queue fanoutBQueue) {
		return BindingBuilder.bind(fanoutBQueue).to(fanoutExchange);
	}
	
	@Bean
	public Binding bindingExchangeC(FanoutExchange fanoutExchange,Queue fanoutCQueue) {
		return BindingBuilder.bind(fanoutCQueue).to(fanoutExchange);
	}
}
