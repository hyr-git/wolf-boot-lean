package com.hyr.lean.rabbitmq.topic.component;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyr.lean.rabbitmq.topic.config.TopicRabbitConfig;

import lombok.extern.slf4j.Slf4j;


/****
 * sender1发送的消息,routing_key是“topic.message”，所以exchange里面的绑定的binding_key是“topic.message”，topic.＃都符合路由规则;所以sender1

发送的消息，两个队列都能接收到；

sender2发送的消息，routing_key是“topic.messages”，所以exchange里面的绑定的binding_key只有topic.＃都符合路由规则;所以sender2发送的消息只有队列

topic.messages能收到。
 * @author m1832
 *
 */
@Slf4j
@Component
public class TopicSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send() {
        String msg1 = "I am topic.mesaage msg======";
        log.info("topic send1: "+msg1);
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EACHANGE,TopicRabbitConfig.TOPIC_MESSAGE_QUEUE,msg1);
        
        String msg2 = "I am topic.mesaages msg########";
        log.info("topic send2: "+msg2);
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EACHANGE,TopicRabbitConfig.TOPIC_MESSAGES_QUEUE,msg2);
	}
}
