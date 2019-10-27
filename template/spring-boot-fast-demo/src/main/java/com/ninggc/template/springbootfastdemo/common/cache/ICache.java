package com.ninggc.template.springbootfastdemo.common.cache;

import java.util.List;

public interface ICache<T> {
    void setString(String key, String value);

    String getString(String key);

    void setList(String key, List<T> list);

    List<T> getList(String key);
}
