package com.ninggc.template.springbootfastdemo.project.dao;

import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import com.ninggc.util.morphia.dao.base.MorphiaBase;
import com.ninggc.util.morphia.dao.base.impl.MorphiaBaseImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

public interface DaoFactory {
    interface BaseDAO extends MorphiaBase<BaseEntity> {}

    @Repository
    class BaseDAOImpl extends MorphiaBaseImpl<BaseEntity> implements BaseDAO {}

    interface UserDAO extends MorphiaBase<UserEntity> {}

    @Repository
    class UserDAOImpl extends MorphiaBaseImpl<UserEntity> implements UserDAO {}
}
