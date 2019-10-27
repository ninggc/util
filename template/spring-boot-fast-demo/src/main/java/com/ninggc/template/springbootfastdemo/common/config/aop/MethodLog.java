package com.ninggc.template.springbootfastdemo.common.config.aop;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MethodLog {
    @AliasFor("value")
    String description() default "";

    @AliasFor("description")
    String value() default "";

    Class<?> clazz() default Object.class;
}
