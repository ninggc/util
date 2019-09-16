package com.ninggc.util.morphia.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 建立对MongoDB的连接
 * 比较简陋，现在使用基于spring的配置，请查看config下的Bean
 */
//@Component
public class MorphiaDatastore {
    @Value("${mongodb.db}")
    private String dbName;
    @Value("${mongodb.uri}")
    private String mongodbUri;

    private Morphia morphia;
    // create the Datastore connecting to the default port on the local host
    private Datastore datastore;
    private MongoClient mongoClient;

    private void init() {
        if (morphia != null && datastore != null && mongoClient != null) {
            return;
        }

        morphia = new Morphia();
//        设置配置实体的包
        morphia.mapPackage("com.wondersgroup.bigdata.project.morphia.domain");

        MongoClientURI mongoClientURI = new MongoClientURI(mongodbUri);
        mongoClient = new MongoClient(mongoClientURI);
        datastore = morphia.createDatastore(mongoClient, dbName);
        datastore.ensureIndexes();
    }

//    @Bean
    public Datastore getDatastoreInstance() {
        this.init();
        return datastore;
    }
}
