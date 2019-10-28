package com.ninggc.template.springbootfastdemo.project.service.impl;

import com.ninggc.template.springbootfastdemo.project.dao.DaoFactory;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import com.ninggc.template.springbootfastdemo.project.service.BaseService;
import com.ninggc.template.springbootfastdemo.project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final DaoFactory.UserDAO userDAO;

    public UserServiceImpl(DaoFactory.UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<UserEntity> getAll() {
        return userDAO.findAll();
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return userDAO.save(entity);
    }
}
