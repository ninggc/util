package com.ninggc.template.springbootfastdemo.web.controller;

import com.ninggc.template.springbootfastdemo.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/base")
public class BaseController {
    @Autowired
    BaseService baseService;

    @GetMapping("/getAll")
    public List<BaseEntity> getAll() {
        return baseService.getAll();
    }
}
