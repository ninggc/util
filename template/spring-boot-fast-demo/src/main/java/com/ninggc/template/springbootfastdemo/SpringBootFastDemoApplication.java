package com.ninggc.template.springbootfastdemo;

import com.ninggc.template.springbootfastdemo.project.dao.SMSRecordMapper;
import com.ninggc.template.springbootfastdemo.project.dao.UserMapper;
import com.ninggc.template.springbootfastdemo.project.domain.SMSRecord;
import com.ninggc.template.springbootfastdemo.project.domain.SMSRecordExample;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ninggc
 */
@SpringBootApplication
//@PropertySource("classpath:morphia.properties")
@EnableSwagger2
@MapperScan("com.ninggc.template.springbootfastdemo.project.dao")
@EnableTransactionManagement
@RestController
public class SpringBootFastDemoApplication implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    AbstractApplicationContext context;

    @Autowired
    UserMapper userMapper;
    @Autowired
    SMSRecordMapper smsRecordMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFastDemoApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("onApplicationEvent");
//        AopContext.currentProxy();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @RequestMapping("test")
    public Object test() {
//        SMSRecordExample example = new SMSRecordExample();
//        example.createCriteria().andIdEqualTo(1);
//        return smsRecordMapper.selectByExample(example);
        return smsRecordMapper.selectByPrimaryKey(1);
//        return userMapper.selectByPrimaryKey(1L);
    }
}
