package com.ninggc.template.springbootfastdemo.common.config.aop;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记是AOP需要再切面执行的adapter
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface AopAdapter {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
