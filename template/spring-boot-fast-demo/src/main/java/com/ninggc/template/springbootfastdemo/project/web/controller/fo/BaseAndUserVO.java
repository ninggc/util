package com.ninggc.template.springbootfastdemo.project.web.controller.fo;

import com.ninggc.template.springbootfastdemo.common.config.aop.action.validate.IVO;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

@Data
@Accessors(chain = true)
public class BaseAndUserVO implements IVO {
    @Transient
    private BaseEntity baseEntity;
    @Transient
    private UserEntity userEntity;
}
