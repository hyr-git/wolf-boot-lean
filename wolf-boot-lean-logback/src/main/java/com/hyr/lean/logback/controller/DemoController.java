package com.hyr.lean.logback.controller;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class DemoController {

    @GetMapping("/index")
    public Object index(){
        String msg="Oh My God ,System Error when "+ DateUtil.now();
        log.error(msg);
        int a = 1/0;
        return ResponseEntity.ok(msg);
    }
}