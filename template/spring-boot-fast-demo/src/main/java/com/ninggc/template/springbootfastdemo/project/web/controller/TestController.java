package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.project.annotation.MethodLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @MethodLog
    @GetMapping("")
//    @MethodLog(value = "test", clazz = TestController.class)
    public String test() {
        return "test";
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
