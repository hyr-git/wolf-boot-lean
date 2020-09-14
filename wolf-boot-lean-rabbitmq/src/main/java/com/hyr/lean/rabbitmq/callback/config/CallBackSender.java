package com.hyr.lean.rabbitmq.callback.config;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallBackSender implements RabbitTemplate.ConfirmCallback{
	
	@Autowired
	private RabbitTemplate rabbitTemplateNew;
	
	public void sendMag(){
		rabbitTemplateNew.setConfirmCallback(this);
		String msg  = "callbackSender : i am callback sender";
		log.info(msg);
		
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		log.info("callbackSender UUID: " + correlationData.getId());  
		rabbitTemplateNew.convertAndSend("topicExchange", "topic.messages", msg, correlationData);  

	}
	
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("callbakck confirm: " + correlationData.getId());
	}
}
