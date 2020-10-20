package com.hyr.lean.rabbitmq.idempotent;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "byte-zb";

    public static final String EXCHANGE_NAME = "byte-zb";

    public static final String ROUTING_KEY = "byte-zb";

    // 队列申明
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }

    // 申明交换机
    @Bean
    public DirectExchange directExchange(){

        return new DirectExchange(EXCHANGE_NAME);
    }

    // 数据绑定申明
    @Bean
    public Binding directBinding(){

        return BindingBuilder.bind(queue()).to(directExchange()).with(ROUTING_KEY);
    }
}