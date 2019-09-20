package com.ninggc.template.springbootfastdemo.web.controller;

import com.ninggc.template.springbootfastdemo.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

public class BaseControllerTest extends AbstractControllerTest {

    @Test
    public void getAll() {
        String url = "/base/getAll";
        mockRequest(url);
    }
}