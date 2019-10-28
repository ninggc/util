package com.ninggc.template.springbootfastdemo.project.service;

import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAll();

    UserEntity save(UserEntity entity);
}
