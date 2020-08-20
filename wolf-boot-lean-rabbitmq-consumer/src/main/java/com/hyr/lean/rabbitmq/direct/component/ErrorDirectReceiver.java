package com.hyr.lean.rabbitmq.direct.component;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
@RabbitListener(
	bindings=@QueueBinding(
			exchange = @Exchange(value="${mq.config.exchange}",type=ExchangeTypes.DIRECT), 
			value = @Queue(value="${mq.config.queue.error}",autoDelete="true"),
			key="${mq.config.queue.error.routing.key}")
)//监听指定的队列
public class ErrorDirectReceiver {

	@RabbitHandler
	public void process(String msg) {
		log.info("ErrorDirectReceiver receive: {}",msg);	
	}
}
