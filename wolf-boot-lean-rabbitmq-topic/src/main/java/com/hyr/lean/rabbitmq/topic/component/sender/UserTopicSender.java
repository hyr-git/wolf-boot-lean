package com.hyr.lean.rabbitmq.topic.component.sender;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/***
 * 消息接收者
 * @author m18323
 * @RabbitListener bindings-绑定队列的值
 * @QueueBinding   value-绑定队列的名称
 *                 exchange-配置交换器
 *                 
 * @Queue value-配置队列名称
 *        autoDelete-是否是一个可删除的临时队列
 *        
 * @Exchange value-为交换器名称
 *           type-指定交换器的类型
 */
@Slf4j
@Component
//监听指定的队列
public class UserTopicSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	//exchange 交互器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	public void sendMsg(String msg) {
		String routingKey = "user.log.debug";
		//向消息队列发送消息
		//log.info("INFODirectSender msg:rtrtrtrt{},gogogo,{} ",message,"099888");	
		this.rabbitTemplate.convertAndSend(exchange, "user.log.debug", "user.log.debug...."+msg+"------"+UUID.randomUUID());
		this.rabbitTemplate.convertAndSend(exchange, "user.log.info", "user.log.info...."+msg+"------"+UUID.randomUUID());
		this.rabbitTemplate.convertAndSend(exchange, "user.log.warn", "user.log.warn...."+msg+"------"+UUID.randomUUID());
		this.rabbitTemplate.convertAndSend(exchange, "user.log.error", "user.log.error...."+msg+"------"+UUID.randomUUID());
	}
}
