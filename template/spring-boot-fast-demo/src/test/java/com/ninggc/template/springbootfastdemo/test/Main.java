package com.ninggc.template.springbootfastdemo.test;

import com.ninggc.template.springbootfastdemo.SpringBootFastDemoApplication;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main implements InitializingBean, BeanNameAware, FactoryBean<UserEntity> {

    public Main() {
        System.out.println("main");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBootFastDemoApplication.class, Main.class);

        Object simpleDateFormat = context.getBean("simpleDateFormat");

        ThreadLocal bean = context.getBean(ThreadLocal.class);

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

    @Override
    public UserEntity getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
