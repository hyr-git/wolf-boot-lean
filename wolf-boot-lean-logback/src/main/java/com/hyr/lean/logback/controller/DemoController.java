package com.hyr.lean.logback.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.lean.logback.model.Student;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;


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
    
    @GetMapping("/name")
    public Object getByName(String name){
        String msg=name+ "》》》》》》Oh My God ,System Error when "+ DateUtil.now();
        log.error(msg);
        return ResponseEntity.ok(msg);
    }
    
    @GetMapping("/list")
    public Object getList(@RequestBody Student sutdent){
        String msg=sutdent.getName()+ "》》》》》》Oh My God ,System Error when "+ DateUtil.now();
        log.error(msg);
        return ResponseEntity.ok(msg);
    }
}