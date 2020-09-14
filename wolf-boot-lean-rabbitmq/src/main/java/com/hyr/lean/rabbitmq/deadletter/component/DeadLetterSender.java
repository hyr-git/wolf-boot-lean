package com.hyr.lean.rabbitmq.deadletter.component;

import java.util.UUID;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyr.lean.rabbitmq.deadletter.config.DeadLetterConfing;

@Component
public class DeadLetterSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMsg(String content) {
        CorrelationData correlation = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
//            设置编码
            messageProperties.setContentEncoding("utf-8");
//            设置过期时间10*1000毫秒
            messageProperties.setExpiration("5000");
            messageProperties.setCorrelationId(correlation.getId());
            return message;
        };
        rabbitTemplate.convertAndSend(DeadLetterConfing.DIRECT_EXCHANGE_NAME, DeadLetterConfing.DEAD_LETTER_ROUTING_KEY, content, messagePostProcessor);
    }
}
