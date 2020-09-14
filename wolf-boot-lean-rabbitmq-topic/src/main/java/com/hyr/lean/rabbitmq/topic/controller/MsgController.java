package com.hyr.lean.rabbitmq.topic.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.rabbitmq.topic.component.sender.OrderTopicSender;
import com.hyr.lean.rabbitmq.topic.component.sender.ProductTopicSender;
import com.hyr.lean.rabbitmq.topic.component.sender.UserTopicSender;

@RestController
public class MsgController {

	@Autowired
	private UserTopicSender userSender;

	@Autowired
	private ProductTopicSender productSender;
	
	@Autowired
	private OrderTopicSender orderSender;
	
	/**
	 * 
	 */
	@GetMapping("/sendMsg")
	public void sendMsg() {
		String uuid = UUID.randomUUID().toString();

		this.userSender.sendMsg("userSender.........msg:{}----》》》"+uuid);
		this.productSender.sendMsg("productSender........msg:{}----》》》"+uuid);
		this.orderSender.sendMsg("orderSender........msg:{}----》》》"+uuid);
		
	}
}
