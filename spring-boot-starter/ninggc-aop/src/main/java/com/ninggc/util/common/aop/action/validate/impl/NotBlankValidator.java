package com.ninggc.util.common.aop.action.validate.impl;

import com.ninggc.util.common.aop.action.validate.ConstraintValidator;
import com.ninggc.util.common.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;

@Validator(NotBlank.class)
public class NotBlankValidator implements ConstraintValidator {
    private static final String DEFAULT_MESSAGE = "不能为空字符串";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        NotBlank anno = ((NotBlank) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(NotBlank.class.getName()) ? DEFAULT_MESSAGE : annoMessage;

        Assert.hasLength((String) value, message);
    }
}
