package com.ninggc.template.springbootfastdemo.project.config.aop;

import com.ninggc.util.common.aop.adapter.anno.AopAdapterConfig;
import com.ninggc.util.common.aop.AopConfiguration;
import com.ninggc.util.common.aop.action.logger.TagEnum;
import com.ninggc.util.common.aop.adapter.impl.LoggerAopAdapter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@AopAdapterConfig(acceptAdapters = LoggerAopAdapter.class)
public class CustomAopConfigurationImpl extends AopConfiguration {

    @Override
    @Pointcut("@annotation(com.ninggc.util.common.aop.MethodLog)")
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return TagEnum.CUSTOM.getValue();
    }
}
