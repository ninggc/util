package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.util.common.aop.AopAdapterConfig;
import com.ninggc.util.common.aop.AopConfiguration;
import com.ninggc.util.common.aop.action.logger.TagEnum;
import com.ninggc.util.common.aop.adapter.impl.LoggerAopAdapter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
@AopAdapterConfig(acceptAdapters = LoggerAopAdapter.class)
public class ServiceAopLoggerConfigurationImpl extends AopConfiguration {

//    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.project.service.impl.*..*(..))")
    @Pointcut("(execution(* com.ninggc.template.springbootfastdemo.project.service..*.*(..))) " +
            "and (@within(org.springframework.stereotype.Service))")
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return TagEnum.SERVICE.getValue();
    }
}
