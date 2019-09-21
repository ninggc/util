package com.ninggc.template.springbootfastdemo.project.service;

import com.ninggc.template.springbootfastdemo.project.AbstractBaseTest;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceTest extends AbstractBaseTest {

    @Autowired BaseService baseService;

    @Test
    public void getAll() {
        List<BaseEntity> all = baseService.getAll();
        Assert.assertEquals(all.size(), all.size());
    }
}