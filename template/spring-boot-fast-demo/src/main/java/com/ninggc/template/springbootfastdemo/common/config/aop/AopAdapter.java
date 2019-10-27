package com.ninggc.template.springbootfastdemo.common.config.aop;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Scope("prototype")
/**
 * 标记需要在切面执行的adapter
 */
public @interface AopAdapter {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
