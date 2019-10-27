package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomAopConfigurationImpl extends AopConfiguration {

    @Override
    @Pointcut("@annotation(com.ninggc.template.springbootfastdemo.common.config.aop.MethodLog)")
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return "MethodLog";
    }
}
