package com.ninggc.template.springbootfastdemo.dao;

import com.ninggc.template.springbootfastdemo.entity.BaseEntity;
import com.ninggc.util.morphia.dao.base.MorphiaBase;
import com.ninggc.util.morphia.dao.base.impl.MorphiaBaseImpl;
import org.springframework.stereotype.Service;

public interface DaoFactory {
    interface MorphiaBaseDAO extends MorphiaBase<BaseEntity> {}

    @Service
    class MorphiaBaseDaoImpl extends MorphiaBaseImpl<BaseEntity> implements MorphiaBaseDAO {}
}
