package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.common.web.ResponseData;
import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.security.Valid;
import com.ninggc.template.springbootfastdemo.project.web.controller.fo.IVO;
import org.aspectj.lang.JoinPoint;

import java.util.Map;

/**
 * 参数校验的adapter
 */
@AopAdapter
public class ValidateAndFormatAopAdapter implements IAopAdapter {
    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Valid) {
                ((Valid) arg).validate();
            }
        }
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        if (returnValue instanceof IVO) {
            Map<String, Object> format = ((IVO) returnValue).format();
            return ResponseData.buildSuccess(format);
        }
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {

    }
}
