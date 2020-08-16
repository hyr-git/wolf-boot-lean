package com.hyr.lean.rabbitmq.message.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDirectController {

	@Autowired
	private UserSender userSender;
	
	 /**
     * 实体类传输测试
     */
    @GetMapping("/sendUser")
    public void userTest() {
           userSender.send();
    }
}
