package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import com.ninggc.template.springbootfastdemo.project.web.controller.fo.BaseAndUserVO;
import com.ninggc.template.springbootfastdemo.project.web.controller.fo.StringFO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "测试用controller")
public class TestController {

    @Autowired
    ApplicationContext context;

    @ApiOperation("获取所有的bean定义名")
    @GetMapping("/beans")
    public String[] beans() {
        return context.getBeanDefinitionNames();
    }

    @ApiOperation("测试一个VO返回两个实体")
    @GetMapping("")
    public Object test(StringFO stringFO) {
        return new BaseAndUserVO().setBaseEntity(new BaseEntity()).setUserEntity(new UserEntity());
    }

    @ApiOperation("Just return 'again'")
    @GetMapping("/again")
    public String again() {
        return "again";
    }

    @ApiOperation("使程序休眠0.5s")
    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(500);
        return "sleep";
    }
}
