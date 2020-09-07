package com.ninggc.template.springbootfastdemo;

import com.google.common.collect.Lists;
import com.ninggc.template.springbootfastdemo.project.dao.SmsRecordMapper;
import com.ninggc.template.springbootfastdemo.project.dao.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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

import java.util.HashMap;
import java.util.List;

/**
 * @author ninggc
 */
@SpringBootApplication
//@PropertySource("classpath:morphia.properties")
@EnableSwagger2
@MapperScan("com.ninggc.template.springbootfastdemo.project.dao")
@EnableTransactionManagement
@RestController
//@EnableDiscoveryClient
public class SpringBootFastDemoApplication implements ApplicationListener<ApplicationStartedEvent>
        , ApplicationRunner, CommandLineRunner {

    @Autowired
    AbstractApplicationContext context;

    @Autowired
    UserMapper userMapper;
    @Autowired
    SmsRecordMapper smsRecordMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFastDemoApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
//        smsRecordMapper.selectById(3);
//        smsRecordMapper.selectById(3);
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("");
    }
}
