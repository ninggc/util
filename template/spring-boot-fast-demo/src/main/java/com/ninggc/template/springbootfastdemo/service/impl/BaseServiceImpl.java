package com.ninggc.template.springbootfastdemo.service.impl;

import com.ninggc.template.springbootfastdemo.dao.DaoFactory;
import com.ninggc.template.springbootfastdemo.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.service.BaseService;
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
}
