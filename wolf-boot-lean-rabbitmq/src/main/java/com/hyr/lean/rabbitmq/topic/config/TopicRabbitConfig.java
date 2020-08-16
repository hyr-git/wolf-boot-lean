package com.hyr.lean.rabbitmq.topic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/****
 * topic是rabbitMQ中最灵活的一种方式,可以根据绑定的binding_key自由的绑定不同的队列
 * 首先对topic规则配置,使用2个队列来进行测试,即topic.message与
 * @author m1832
 *      *表示一个词,#表示零个或多个词
 */
@Configuration
public class TopicRabbitConfig {

	public static final String TOPIC_MESSAGE_QUEUE ="topic.message";
	
	public static final String TOPIC_MESSAGES_QUEUE ="topic.messages";
	
	public static final String TOPIC_EACHANGE = "topicExchange";
	
	public static final String TOPIC_BINDING_KEY = "topic.message";
	
	public static final String TOPIC_MATCH_BINDING_KEY = "topic.#";

	@Bean
	public Queue queueMesage(){
		return new Queue(TOPIC_MESSAGE_QUEUE);
	}
	@Bean
	public Queue queueMesages(){
		return new Queue(TOPIC_MESSAGES_QUEUE);
	}
	
	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EACHANGE);
	}
	
	/***
	 * 将队列topic.message与topicExchange绑定通过binding_key为topic.message,就是完全匹配
	 * @param queueMesage
	 * @param topicExchange
	 * @return
	 */
	@Bean
    Binding bindingExchangeMessage(Queue queueMesage,TopicExchange topicExchange) {
       return BindingBuilder.bind(queueMesage).to(topicExchange).with(TOPIC_BINDING_KEY);
	}
	
	/***
	 * 将队列topic.messages与topicExchange绑定通过binding_key为为topic.#,模糊匹配
	 * @param queueMesage
	 * @param topicExchange
	 * @return
	 */
	@Bean
    Binding bindingExchangeMessages(Queue queueMesages,TopicExchange topicExchange) {
       return BindingBuilder.bind(queueMesages).to(topicExchange).with(TOPIC_MATCH_BINDING_KEY);
	}
}
