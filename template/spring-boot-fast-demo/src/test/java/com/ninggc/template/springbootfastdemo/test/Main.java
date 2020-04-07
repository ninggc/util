package com.ninggc.template.springbootfastdemo.test;

import com.ninggc.template.springbootfastdemo.SpringBootFastDemoApplication;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main implements InitializingBean, BeanNameAware {

    public Main() {
        System.out.println("main");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBootFastDemoApplication.class, Main.class);

        System.out.println("end");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("setBeanName");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
