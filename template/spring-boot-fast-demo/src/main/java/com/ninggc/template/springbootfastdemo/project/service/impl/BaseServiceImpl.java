package com.ninggc.template.springbootfastdemo.project.service.impl;

import com.ninggc.template.springbootfastdemo.project.dao.DaoFactory;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import com.ninggc.template.springbootfastdemo.project.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {

    private final DaoFactory.BaseDAO baseDAO;

    public BaseServiceImpl(DaoFactory.BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    @Override
    public List<BaseEntity> getAll() {
        return baseDAO.findAll();
    }

    public BaseEntity save(BaseEntity baseEntity) {
        return baseDAO.save(baseEntity);
    }
}
