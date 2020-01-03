package com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.ConstraintValidator;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

@Validator(NotEmpty.class)
public class NotEmptyValidator implements ConstraintValidator {
    private static final String defaultMessage = "必须至少含有一个元素";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        NotEmpty anno = ((NotEmpty) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(NotEmpty.class.getName()) ? defaultMessage : annoMessage;

        if (value instanceof Collection) {
            Assert.notEmpty((Collection<?>) value, message);
        } else if (value instanceof Map) {
            Assert.notEmpty((Map<?, ?>) value, message);
        }
    }
}
