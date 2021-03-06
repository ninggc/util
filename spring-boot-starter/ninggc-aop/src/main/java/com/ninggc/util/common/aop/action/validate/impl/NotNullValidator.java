package com.ninggc.util.common.aop.action.validate.impl;

import com.ninggc.util.common.aop.action.validate.ConstraintValidator;
import com.ninggc.util.common.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

/**
 * @author ninggc
 */
@Validator(NotNull.class)
public class NotNullValidator implements ConstraintValidator {
    private static final String DEFAULT_MESSAGE = "不能为null";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        NotNull anno = ((NotNull) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(NotNull.class.getName()) ? DEFAULT_MESSAGE : annoMessage;

        Assert.notNull(value, message);
    }
}
