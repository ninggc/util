package com.ninggc.template.springbootfastdemo.web;

import com.ninggc.template.springbootfastdemo.AbstractBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractControllerTest extends AbstractBaseTest {
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Override
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
