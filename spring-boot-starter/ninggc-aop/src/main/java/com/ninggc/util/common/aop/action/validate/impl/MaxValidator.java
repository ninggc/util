package com.ninggc.util.common.aop.action.validate.impl;

import com.ninggc.util.common.aop.action.validate.ConstraintValidator;
import com.ninggc.util.common.aop.action.validate.Validator;
import org.springframework.util.Assert;

import javax.validation.constraints.Max;
import java.lang.annotation.Annotation;

/**
 * @author ninggc
 */
@Validator(Max.class)
public class MaxValidator implements ConstraintValidator {
    private static final String DEFAULT_MESSAGE = "超出最大值";

    @Override
    public void isValid(Object value, Annotation annotation) throws IllegalArgumentException {
        Max anno = ((Max) annotation);
        String annoMessage = anno.message();
        String message = annoMessage.contains(Max.class.getName()) ? DEFAULT_MESSAGE : annoMessage;

        Assert.isTrue((Double) value < anno.value(), message);
    }
}
