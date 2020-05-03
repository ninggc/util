package com.ninggc.template.springbootfastdemo.project.dao;

import com.ninggc.template.springbootfastdemo.project.domain.User;
import com.ninggc.template.springbootfastdemo.project.domain.UserExample;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}