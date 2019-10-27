package com.ninggc.template.springbootfastdemo.common.cache;

import com.ninggc.template.springbootfastdemo.project.AbstractBaseTest;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.service.BaseService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RedisCacheTest extends AbstractBaseTest {

    @Autowired
    ICache<BaseEntity> iCache;
    @Autowired
    BaseService baseService;
    @Autowired
    SimpleDateFormat sdf;

    private Date date;
    private List<BaseEntity> allEntity;

    @Override
    public void before() {
        super.before();
        date = new Date();
        allEntity = baseService.getAll();
    }

    @Override
    public void after() {
        super.after();
    }

    @Test
    public void cacheString() {
        iCache.setString("test", sdf.format(date));

        String string = iCache.getString("test");
        Assert.assertEquals(sdf.format(date), string);

        putResult(string);
    }

    @Test
    public void cacheList() {
        iCache.setList("testList", allEntity);

        List<BaseEntity> testList = iCache.getList("testList");
        Assert.assertEquals(testList.size(), allEntity.size());

        putResult(testList);
    }
}