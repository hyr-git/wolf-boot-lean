package com.hyr.lean.rabbitmq.topic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.rabbitmq.topic.component.TopicSender;

@RestController
public class TopicController {

	@Autowired
	private TopicSender topicSender;
	
	/**
     * topic exchange类型rabbitmq测试
     */
    @GetMapping("/topicTest")
    public void topicTest() {
           topicSender.send();
    }
}
