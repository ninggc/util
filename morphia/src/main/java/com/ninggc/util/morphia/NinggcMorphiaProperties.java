package com.ninggc.util.morphia;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "mongodb")
public class NinggcMorphiaProperties {
    private String ip;
    private String port;
    private String db;
    private String username;
    private String password;
    private String uri;

    private List<String> packages;
}
