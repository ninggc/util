package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.common.security.Valid;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public class StringFO implements Valid {

    private String str;

    @Override
    public void validate() throws IllegalArgumentException {
        Assert.hasLength(str, "str should not be null or blank");
    }
}
