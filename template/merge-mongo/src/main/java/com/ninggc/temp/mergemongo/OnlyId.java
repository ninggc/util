package com.ninggc.temp.mergemongo;

import java.io.Serializable;

public class OnlyId implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
