package com.ninggc.template.springbootfastdemo.project;

import com.google.gson.Gson;
import com.ninggc.template.springbootfastdemo.SpringBootFastDemoApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootFastDemoApplication.class)
public class AbstractBaseTest {
    @Autowired
    protected Gson gson;

    private Object resultObject;

    protected void putResult(Object obj) {
        this.resultObject = obj;
    }

    @Before
    public void before() {

    }

    @After
    public void after() {
        Optional.ofNullable(resultObject).map(o -> {
            System.out.println(gson.toJson(resultObject));
            return null;
        });
    }
}
