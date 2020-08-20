package com.hyr.lean.rabbitmq.direct.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.rabbitmq.direct.component.sender.ErrorDirectSender;
import com.hyr.lean.rabbitmq.direct.component.sender.InfoDirectSender;

@RestController
public class MsgController {

	@Autowired
	private InfoDirectSender infoDirectSender;

	@Autowired
	private ErrorDirectSender errorDirectSender;
	
	/**
	 * 
	 */
	@GetMapping("/sendMsg")
	public void sendMsg() {
		String uuid = UUID.randomUUID().toString();
		infoDirectSender.sendMsg("info  msg:{}----》》》"+uuid);
	}
	
	/**
	 * 
	 */
	@GetMapping("/sendErrorMsg")
	public void sendErrorMsg() {
		String uuid = UUID.randomUUID().toString();
		errorDirectSender.sendMsg("error  msg:{}----"+uuid+new Date());
	}
}
