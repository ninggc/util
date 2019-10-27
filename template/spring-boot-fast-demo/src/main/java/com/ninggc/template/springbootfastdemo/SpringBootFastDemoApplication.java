package com.ninggc.template.springbootfastdemo;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.project.config.aop.ControllerAopLoggerConfigurationImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@SpringBootApplication
@PropertySource("classpath:morphia.properties")
public class SpringBootFastDemoApplication implements ApplicationListener<ApplicationStartedEvent> {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFastDemoApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
    }
}
