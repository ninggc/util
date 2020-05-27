package com.ninggc.template.springbootfastdemo.project.config.aop.adapter;

import com.ninggc.common.web.ResponseData;
import com.ninggc.util.common.aop.adapter.IAopAdapter;
import com.ninggc.util.common.aop.adapter.anno.AopAdapter;
import com.ninggc.util.common.aop.util.IUtilGson;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.annotation.Scope;

@AopAdapter
@Scope("prototype")
public class WrapVOAdapter implements IAopAdapter, IUtilGson {
    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {

    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        if (returnValue instanceof ResponseData) {
            return returnValue;
        } else {
            // 包装controller的返回值
            return ResponseData.buildSuccess(returnValue);
        }
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {

    }
}
