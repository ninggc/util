package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.ConstraintValidator;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;

@Validator(Min.class)
public class MinValidator implements ConstraintValidator {
    private static final String defaultMessage = "低于最小值";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        Min anno = ((Min) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(Min.class.getName()) ? defaultMessage : annoMessage;

        Assert.isTrue(((Double) value) > anno.value(), message);
    }
}
