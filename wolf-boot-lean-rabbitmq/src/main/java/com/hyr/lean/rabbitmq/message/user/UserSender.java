package com.hyr.lean.rabbitmq.message.user;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        User user=new User();
        user.setName("hzb");
        user.setPass("123456789");
        user.setBirthday(new Date());
        log.info("user send : " + user);
        this.rabbitTemplate.convertAndSend(UserDirectRabbitConfig.queueName, user);
    }

}