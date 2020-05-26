package com.ninggc.util.common.aop.adapter.impl;

import com.ninggc.util.common.aop.AopAdapter;
import com.ninggc.util.common.aop.action.logger.IAopLoggerHandler;
import com.ninggc.util.common.aop.action.logger.TagEnum;
import com.ninggc.util.common.aop.adapter.IAopAdapter;
import com.ninggc.util.common.aop.interceptor.UrlLogInterceptor;
import com.ninggc.util.common.aop.util.IUtilGson;
import com.ninggc.util.common.aop.util.IUtilLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;

@AopAdapter
public class ExceptionProcessAdapter implements IAopAdapter, IUtilGson {
    private IAopLoggerHandler aopLoggerHandler;

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {

        // 找到方法上标注了@RequestBody注解的字段
        Integer foWithRequestBody = -1;
        if (TagEnum.CONTROLLER.equals(aopLoggerHandler.getTag())) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            if (signature.getMethod().getAnnotation(PostMapping.class) != null) {
                for (int i1 = 0; i1 < signature.getMethod().getParameterAnnotations().length; i1++) {
                    for (Annotation annotation : signature.getMethod().getParameterAnnotations()[i1]) {
                        if (annotation instanceof RequestBody) {
                            foWithRequestBody = i1;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (foWithRequestBody.equals(i)) {
                String argsShow = gson.toJson(args[i]);

                // 将标注了@RequestBody的字段的值给到拦截器上
                StringBuilder curlBuilder = UrlLogInterceptor.curlBuilder.get();
                if (curlBuilder != null) {
                    curlBuilder.append(" --data-raw \"").append(argsShow).append("\" ");
                    info(curlBuilder.toString());
                }
                break;
            }
        }
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {
    }
}
