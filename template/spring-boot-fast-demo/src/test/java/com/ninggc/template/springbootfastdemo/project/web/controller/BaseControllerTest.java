package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.project.web.AbstractControllerTest;
import org.junit.Test;

public class BaseControllerTest extends AbstractControllerTest {

    @Test
    public void getAll() {
        String url = "/base/getAll";
        mockRequest(url);
    }
}