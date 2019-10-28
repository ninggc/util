package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.common.config.aop.MethodLog;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import com.ninggc.template.springbootfastdemo.project.web.controller.fo.BaseAndUserVO;
import com.ninggc.template.springbootfastdemo.project.web.controller.fo.StringFO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/beans")
    public String[] beans() {
        return context.getBeanDefinitionNames();
    }

    @MethodLog
    @GetMapping("")
    public Object test(StringFO stringFO) {
        return new BaseAndUserVO().setBaseEntity(new BaseEntity()).setUserEntity(new UserEntity());
    }

    @GetMapping("/again")
    public String again() {
        return "again";
    }

    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(500);
        return "sleep";
    }
}
