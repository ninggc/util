package com.ninggc.template.springbootfastdemo.config.aop.impl;

import com.ninggc.template.springbootfastdemo.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.config.aop.adapter.IAopLogAdapter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomAopConfigurationImpl extends AopConfiguration {
    public CustomAopConfigurationImpl(IAopLogAdapter aopLogAdapter) {
        super(aopLogAdapter);
    }

    @Override
    @Pointcut("@annotation(com.ninggc.template.springbootfastdemo.annotation.MethodLog)")
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return "MethodLog";
    }
}
