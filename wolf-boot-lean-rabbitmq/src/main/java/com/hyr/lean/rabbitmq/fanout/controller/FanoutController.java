package com.hyr.lean.rabbitmq.fanout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.rabbitmq.fanout.component.FanoutSender;

@RestController
public class FanoutController {

	@Autowired
	private FanoutSender fanoutSender;
	
	 /**
     * fanout exchange类型rabbitmq测试
     */
    @GetMapping("/fanout")
    public void fanoutTest() {
           fanoutSender.sendMsg();
    }
}
