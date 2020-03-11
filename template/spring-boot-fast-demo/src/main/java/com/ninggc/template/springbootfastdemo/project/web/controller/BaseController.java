package com.ninggc.template.springbootfastdemo.project.web.controller;

import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/base")
@Api(tags = "只有id的测试实体的相关接口")
public class BaseController {
    private final BaseService baseService;

    public BaseController(BaseService baseService) {
        this.baseService = baseService;
    }

    @ApiOperation(("获取所有base实体"))
    @GetMapping("/all")
    public List<BaseEntity> getAll() {
        return baseService.getAll();
    }

    @ApiOperation("新增base实体")
    @PutMapping("")
    public BaseEntity putBaseEntity() {
        return baseService.save(new BaseEntity());
    }
}
