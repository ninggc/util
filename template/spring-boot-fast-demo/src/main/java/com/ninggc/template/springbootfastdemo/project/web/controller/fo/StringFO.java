package com.ninggc.template.springbootfastdemo.project.web.controller.fo;

import com.ninggc.template.springbootfastdemo.common.security.Valid;
import com.ninggc.util.common.aop.action.validate.Validate;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ninggc
 */
@Data
@Validate
public class StringFO implements Valid {

    @NotNull
    private String str;

    @Override
    public void validate() throws IllegalArgumentException {
//        Assert.hasLength(str, "str should not be null or blank");
    }
}
