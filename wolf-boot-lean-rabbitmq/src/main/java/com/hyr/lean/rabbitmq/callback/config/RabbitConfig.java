package com.hyr.lean.rabbitmq.callback.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


/***
 * 增加回调类,不在使用applicaiton.properties默认配置方式。
 * ，会在程序中显示的使用文件中的配置信息。该示例中没有新建队列和exchange，用的是第5节中的topic.messages队列和exchange转发器。消费者也是第5节中的topicMessagesReceiver
rabbitmq配置类：
 * @author m1832
 *
 */
public class RabbitConfig {

	@Value("${spring.rabbitmq.host}")
	private String addresses;
	
	@Value("${spring.rabbitmq.port}")
	private String port;
	
	@Value("${spring.rabbitmq.userName}")
	private String userName;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;
	
	@Value("${spring.rabbitmq.publisher-confirms}")
	private boolean publisherConfirms;
	
	
	@Bean
	public ConnectionFactory connectionFactory() {
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(addresses+":"+port);
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		/**
		 * 若要进行消息回调,则这里必须要设置为true
		 */
		connectionFactory.setPublisherConfirms(publisherConfirms);
		return connectionFactory;
	}
	
	@Bean
	//因为要设置回调类，所以应该是prototype类型,如果是singleton类型，则回调类为最后一次设置
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplateNew(){
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}
}
