package com.ninggc.template.springbootfastdemo.project;

import com.google.gson.Gson;
import com.ninggc.template.springbootfastdemo.SpringBootFastDemoApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootFastDemoApplication.class)
public class AbstractBaseTest {
    @Autowired
    protected Gson gson;

    @Before
    public void before() {

    }

    @After
    public void after() {

    }
}
