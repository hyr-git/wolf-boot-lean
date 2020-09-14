package com.hyr.lean.rabbitmq.topic.component.receiver;

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
//监听指定的队列
@RabbitListener(
		bindings=@QueueBinding(
			   //@queue---当所有消费客户端连接断开后,是否自动删除队列
			   //@Exchange----当所有绑定队列都不在使用时,是否自动删除交换器
			   value=@Queue(value="${mq.config.queue.red}",autoDelete="true"),//指定队列
		       exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.FANOUT)//指定交换器
		      // key="*.log.info"// 队列与交换器的绑定关系
		)
)
public class RedFanoutReceiver {

	/***
	 * 采用消息队列监听机制
	 * @param msg
	 */
	@RabbitHandler
	public void process(String msg) {
		log.info("给用户发送红包10，red ... fanout ... Receiver receive:{} ",msg);	
	}
}
