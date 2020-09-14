package com.hyr.lean.rabbitmq.callback.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hyr.lean.rabbitmq.topic.config.TopicRabbitConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RabbitListener(queues=TopicRabbitConfig.TOPIC_MESSAGES_QUEUE)
public class TopicCallBackMessagesReceiver {

	
	@RabbitHandler
	public void process(String msg) {
		log.info("TopicCallBackMessagesReceiver  : " +msg);
	}
}
