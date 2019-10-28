package com.ninggc.template.springbootfastdemo.project.web.controller.fo;

import com.google.gson.reflect.TypeToken;
import com.ninggc.template.springbootfastdemo.common.util.IUtilGson;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import com.ninggc.template.springbootfastdemo.project.entity.UserEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.util.Map;

@Data
@Accessors(chain = true)
public class BaseAndUserVO implements IVO, IUtilGson {
    @Transient
    private BaseEntity baseEntity;
    @Transient
    private UserEntity userEntity;

    @Override
    public Map<String, Object> format() {
        String s1 = gson.toJson(baseEntity);
        String s2 = gson.toJson(userEntity);

        Map<String, Object> map = gson.fromJson(s1, new TypeToken<Map<String, Object>>(){}.getType());
        Map<String, Object> map2 = gson.fromJson(s1, new TypeToken<Map<String, Object>>(){}.getType());
        map.putAll(map2);

        return map;
    }
}
