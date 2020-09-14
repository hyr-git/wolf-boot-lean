package com.hyr.lean.rabbitmq.direct.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hyr.lean.rabbitmq.direct.config.DirectRabbitConfig;

import lombok.extern.slf4j.Slf4j;


/***
 * 消费者
 * @author m1832
 *
 */
@Slf4j
@Component
@RabbitListener(queues=DirectRabbitConfig.queueName)//监听指定的队列
public class DirectReceiver {

	@RabbitHandler
	public void process(String msg) {
		log.info("DirectReceiver receive: "+msg);	
	}
}
