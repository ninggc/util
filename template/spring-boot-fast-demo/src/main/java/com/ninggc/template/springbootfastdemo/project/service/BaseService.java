package com.ninggc.template.springbootfastdemo.project.service;

import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;

import java.util.List;

public interface BaseService {
    List<BaseEntity> getAll();

    BaseEntity save(BaseEntity baseEntity);
}
