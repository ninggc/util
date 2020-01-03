package com.ninggc.template.springbootfastdemo.project.web.controller.fo;

import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.Validate;
import com.ninggc.template.springbootfastdemo.common.security.Valid;
import lombok.Data;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

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
