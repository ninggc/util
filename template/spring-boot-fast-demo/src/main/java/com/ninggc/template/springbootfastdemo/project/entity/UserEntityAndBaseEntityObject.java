package com.ninggc.template.springbootfastdemo.project.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 90697
 * @version $Id: UserEntityAndBaseEntityObject.java, v 0.1 2019-10-29 19:54:08 90697 Exp $$
 */
@Data
class UserEntityAndBaseEntityObject {

    @Transient
    private UserEntity userEntity;
    @Transient
    private BaseEntity baseEntity;
    private static Map<String, Object> map;

    public static Map<String, Object> convertToMap(UserEntity userEntity, BaseEntity baseEntity) {
        map = new HashMap<>();

        map.put("username", userEntity.getUsername());
        map.put("token", userEntity.getToken());
        map.put("cookie", userEntity.getCookie());


        return map;
    }
}