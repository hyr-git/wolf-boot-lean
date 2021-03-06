package com.hyr.lean.rabbitmq.fanout.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hyr.lean.rabbitmq.fanout.config.FanoutRabbitConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RabbitListener(queues=FanoutRabbitConfig.FANOUT_C_QUEUE_NAME)
public class FanoutReceiverC {

	@RabbitHandler
	public void proccess(String msg) {
		log.info("FanoutReceiverC  : " + msg);
	}
}
