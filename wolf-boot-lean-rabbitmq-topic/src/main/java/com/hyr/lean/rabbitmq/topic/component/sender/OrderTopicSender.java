package com.hyr.lean.rabbitmq.topic.component.sender;

import org.springframework.amqp.core.AmqpTemplate;
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
public class OrderTopicSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	public void sendMsg(String message) {
		//向消息队列发送消息
		log.info("INFODirectReceiver receive:{} ",message);	
		this.rabbitTemplate.convertAndSend(exchange, "order.log.deubg", "order.log.deubg....."+ message);
		this.rabbitTemplate.convertAndSend(exchange, "order.log.info", "order.log.info....."+ message);
		this.rabbitTemplate.convertAndSend(exchange, "order.log.warn", "order.log.warn....."+ message);
		this.rabbitTemplate.convertAndSend(exchange, "order.log.error", "order.log.error....."+ message);
	}
}
