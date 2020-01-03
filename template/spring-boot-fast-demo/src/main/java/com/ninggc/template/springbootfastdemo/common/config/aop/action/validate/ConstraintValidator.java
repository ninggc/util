package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate;

import java.lang.annotation.Annotation;

/**
 * @see javax.validation.ConstraintValidator
 */
public interface ConstraintValidator {
    /**
     *
     * @param value 需要检验的对象
     * @param annotation
     * @throws IllegalArgumentException 检验未通过
     */
    void isValid(Object value, Annotation annotation) throws IllegalArgumentException;
}

