package com.ninggc.template.springbootfastdemo.common.cache;

import com.google.gson.Gson;
import com.ninggc.template.springbootfastdemo.project.entity.BaseEntity;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RedisCache implements ICache<BaseEntity> {
    private final Jedis jedis;
    private final Gson gson;

    public RedisCache(Jedis jedis, Gson gson) {
        this.jedis = jedis;
        this.gson = gson;
    }

    @Override
    public void setString(String key, String value) {
//        jedis.sadd(key, value);
        jedis.append(key, value);
    }

    @Override
    public String getString(String key) {
        return jedis.get(key);
    }

    @Override
    public void setList(String key, List<BaseEntity> list) {
        List<String> collect = list.stream()
                .map(gson::toJson)
                .collect(Collectors.toList());
        jedis.lpush(key, collect.toArray(new String[]{}));
    }

    @Override
    public List<BaseEntity> getList(String key) {
        return jedis.lrange(key, 0, jedis.llen(key))
                .stream()
                .map(s -> gson.fromJson(s, BaseEntity.class))
                .collect(Collectors.toList()
                );
    }
}
