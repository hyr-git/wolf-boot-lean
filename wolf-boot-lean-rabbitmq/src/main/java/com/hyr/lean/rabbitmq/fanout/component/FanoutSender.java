package com.hyr.lean.rabbitmq.fanout.component;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.hyr.lean.rabbitmq.fanout.config.FanoutRabbitConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FanoutSender {

	@Resource
	private AmqpTemplate rabbitTemplate;
	
	public void sendMsg() {
		String msg = "fanoutSender :hello i am hzb";
		log.info(msg);
		rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE,"abcd.ee",msg);
	}
	
}
