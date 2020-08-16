package com.hyr.lean.rabbitmq.callback.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicCallBackController {

	@Autowired
	private CallBackSender callBackSender;
	
	/**
     * topic exchange类型rabbitmq测试
     */
    @GetMapping("/callbak")
    public void callbak() {
        callBackSender.sendMag();
    }
}
