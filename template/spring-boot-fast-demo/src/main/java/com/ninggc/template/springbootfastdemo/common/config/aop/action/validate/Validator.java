package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记可以做检验的成员
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Validator {
    /**
     * 该检验成员检验的规范
     * @return
     */
    Class value();
}
