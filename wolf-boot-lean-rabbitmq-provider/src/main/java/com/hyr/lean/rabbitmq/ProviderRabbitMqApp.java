package com.hyr.lean.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * [zheng]https://www.cnblogs.com/weirdo-lenovo/p/10660671.html
 * https://www.cnblogs.com/boshen-hzb/p/6841982.html 
 * https://www.cnblogs.com/hlhdidi/p/6535677.html
 * 交换器的主要作用---接收响应的消息并绑定到指定的队列
 * 交换的类型---direct、topic、headers、fanout
 *     direct是rabbitmq默认的交换器,即创建消息队列的时候,指定一个BindingKey【直接匹配】。
 *     topic转发消息主要是依据通配符,队列和交换器的绑定主要是依据一种模式(通配符+字符串),
 *     而当发送消息的时候,只有指定的key和该模式相配的消息，消息才会被发送到该消息队列中。
 *     fanout是路由广播的形式,将会把消息发给绑定他的全部队列，即便设置了key也会被忽略。
 * @author m1832
 * 
 * AMQP的主要特征是面向消息、队列、路由(包括点对点和发布订阅)、可靠性、安全
 * 用于在分布式系统中存储转发消息,在易用性、可扩展、高可用等。
 * 
 * Virtual Host 虚拟主机,表示一批交换器,消息队列和相关对象。是共享相同的身份认证和加密环境的独立服务器阈。
 *      每一个vhost就是一个mini版的rabbitMQ服务器,拥有自己的队列、交换器、绑定和权限机制。
 * Broker  消息队列服务器实体     
 * 
 * 
 */
@SpringBootApplication
public class ProviderRabbitMqApp 
{
    public static void main( String[] args )
    {
         SpringApplication.run(ProviderRabbitMqApp.class, args);
    }
}
