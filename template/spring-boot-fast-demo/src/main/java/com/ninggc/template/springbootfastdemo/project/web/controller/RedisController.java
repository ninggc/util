package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.common.base.BaseAbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController extends BaseAbstractController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @PostMapping("/set")
    public void set() {
        stringRedisTemplate.opsForSet().add("set", String.valueOf(System.currentTimeMillis()));
        stringRedisTemplate.opsForValue().set("value1", "aaa");

    }
}
