package com.ninggc.util.morphia;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "mongodb")
public class NinggcMorphiaProperties implements InitializingBean {
    private String ip;
    private String port;
    private String db;
    private String username;
    private String password;
    private String uri;

    private List<String> packages;

    @Override
    public void afterPropertiesSet() throws Exception {
        ip = ip == null ? "localhost" : ip;
        port = port == null ? "27017" : port;
        if (db == null) {
            throw new Exception("必须指定 mongodb.db ");
        }
        username = username == null ? "" : username;
        password = password == null ? "" : password;
        String s = "mongodb://" + username + ":" + password + "@" + ip + ":" + port + "/" + db + "?authSource=admin";
        uri = uri == null ? s : uri;
    }
}
