package com.hyr.lean.rabbitmq.direct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.rabbitmq.direct.component.DirectSender;

@RestController
public class DirectController {

	@Autowired
	private DirectSender directSender;
	
	@GetMapping("/sendMsg")
	public String sendMsg() {
		directSender.sendMsg("");
		return "发送成功！";
	}
	
	/**
     * 单生产者-多消费者  
     * 多个消费端共同处理所有的消息
	 */
	@GetMapping("/sendMsgMany")
	public void oneToMany() {
		for (int i = 0; i < 10; i++) {
			directSender.sendMsg("hellomsg:" + i);
		}

	}
}
