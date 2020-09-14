package com.hyr.lean.rabbitmq.topic.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.rabbitmq.topic.component.sender.FanoutSender;

@RestController
public class MsgController {

	@Autowired
	private FanoutSender fanoutSender;

	/**
	 * 
	 */
	@GetMapping("/sendMsg")
	public void sendMsg() {
		String uuid = UUID.randomUUID().toString();

		this.fanoutSender.sendMsg("fanoutSender.........msg:{}----》》》"+uuid);
		
	}
}
