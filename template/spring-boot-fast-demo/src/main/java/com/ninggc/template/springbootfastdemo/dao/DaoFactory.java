package com.ninggc.template.springbootfastdemo.dao;

import com.ninggc.template.springbootfastdemo.entity.BaseEntity;
import com.ninggc.util.morphia.dao.base.MorphiaBase;
import com.ninggc.util.morphia.dao.base.impl.MorphiaBaseImpl;
import org.springframework.stereotype.Service;

public interface DaoFactory {
    interface BaseDAO extends MorphiaBase<BaseEntity> {}

    @Service
    class BaseDAOImpl extends MorphiaBaseImpl<BaseEntity> implements BaseDAO {}
}
