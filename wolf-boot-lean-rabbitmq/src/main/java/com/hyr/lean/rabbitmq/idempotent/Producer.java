package com.hyr.lean.rabbitmq.idempotent;

import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/sendMiDeng")
    public void sendMessage(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email","11111111111");
        jsonObject.put("timestamp",System.currentTimeMillis());

        String json = jsonObject.toJSONString();
        log.info(json);
        
        Message message = MessageBuilder.withBody(json.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("UTF-8").setMessageId(UUID.randomUUID().toString()).build();
        amqpTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,RabbitConfig.QUEUE_NAME,message);
    }

}