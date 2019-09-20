package com.ninggc.template.springbootfastdemo;

import com.ninggc.template.springbootfastdemo.dao.DaoFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootFastDemoApplicationTests {

    @Autowired
    DaoFactory.BaseDAO baseDAO;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(baseDAO);
    }

}
