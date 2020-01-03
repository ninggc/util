package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.ConstraintValidator;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

@Validator(NotNull.class)
public class NotNullValidator implements ConstraintValidator {
    private static final String defaultMessage = "不能为null";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        NotNull anno = ((NotNull) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(NotNull.class.getName()) ? defaultMessage : annoMessage;

        Assert.notNull(value, message);
    }
}
