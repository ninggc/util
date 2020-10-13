package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.project.dao.SmsRecordMapper;
import com.ninggc.template.springbootfastdemo.project.domain.BdSmsRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ninggc
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试用controller")
public class TestController {

    @Autowired
    ApplicationContext context;
    @Autowired
    private SmsRecordMapper smsRecordMapper;


    @RequestMapping()
    public Object test() {
        // smsRecordMapper.selectPage()
        // SqlSession sqlSession = context.getBean(SqlSession.class);
        // List<Object> objects = sqlSession.selectList("select 1;", null);
        BdSmsRecord bdSmsRecord = smsRecordMapper.selectById(3);
        BdSmsRecord bdSmsRecord1 = smsRecordMapper.selectById(4);
        return null;
    }

    @ApiOperation("获取所有的bean定义名")
    @GetMapping("/beans")
    public String[] beans() {
        return context.getBeanDefinitionNames();
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
