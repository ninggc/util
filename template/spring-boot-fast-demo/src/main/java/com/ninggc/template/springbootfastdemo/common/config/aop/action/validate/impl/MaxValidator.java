package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.ConstraintValidator;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import java.lang.annotation.Annotation;

@Validator(Max.class)
public class MaxValidator implements ConstraintValidator {
    private static final String defaultMessage = "超出最大值";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        Max anno = ((Max) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(Max.class.getName()) ? defaultMessage : annoMessage;

        Assert.isTrue((Double) value < anno.value(), message);
    }
}
