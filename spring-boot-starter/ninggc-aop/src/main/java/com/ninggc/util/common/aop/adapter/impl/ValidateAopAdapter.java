package com.ninggc.util.common.aop.adapter.impl;

import com.ninggc.util.common.aop.adapter.anno.AopAdapter;
import com.ninggc.util.common.aop.action.validate.ConstraintValidator;
import com.ninggc.util.common.aop.action.validate.Validate;
import com.ninggc.util.common.aop.action.validate.Validator;
import com.ninggc.util.common.aop.adapter.IAopAdapter;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数校验的adapter
 */
@AopAdapter
@Validator(Validate.class)
public class ValidateAopAdapter implements IAopAdapter, ApplicationListener<ContextRefreshedEvent>, ConstraintValidator {
    @Value("${aop.switch.validate:false}")
    private Boolean aopSwitch;

    private Map<String, List<Object>> map;

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        for (Field field : value.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(value);
                for (Annotation anno : field.getAnnotations()) {
                    // 找到对象内字段的注解，并使用相应的ConstraintValidator进行处理
                    List<Object> list = map.get(anno.annotationType().getName());
//                    List<Object> list = map.get(((AnnotationDescription.AnnotationInvocationHandler) ((Proxy) anno).h).type.getName());
                    if (list != null) {
                        for (Object o : list) {
                            if (o instanceof ConstraintValidator) {
                                try {
                                    ((ConstraintValidator) o).isValid(fieldValue, anno);
                                } catch (IllegalArgumentException e) {
                                    throw new IllegalArgumentException(field.getName() + e.getMessage());
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException ignore) {
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> beansWithAnnotation = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(Validator.class);
        map = beansWithAnnotation
                .values().stream()
                .collect(
                        Collectors.groupingBy(x -> x.getClass().getAnnotation(Validator.class).value().getName())
                );
    }

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) throws IllegalArgumentException {
        if (!aopSwitch) {
            return;
        }

        for (Object arg : args) {
            Validate annotation = arg.getClass().getAnnotation(Validate.class);
            if (annotation != null) {
                isValid(arg, annotation);
            }
        }
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        if (!aopSwitch) {
            return null;
        }

        return null;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {
        if (!aopSwitch) {
            return;
        }
    }
}
