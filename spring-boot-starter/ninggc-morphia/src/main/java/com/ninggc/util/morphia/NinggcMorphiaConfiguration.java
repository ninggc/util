package com.ninggc.util.morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({NinggcMorphiaProperties.class})
@PropertySource("classpath:morphia.properties")
@ConditionalOnProperty(value = "", prefix = "mongodb")
public class NinggcMorphiaConfiguration {
    private final NinggcMorphiaProperties properties;

    public NinggcMorphiaConfiguration(NinggcMorphiaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Morphia morphia() {
        Morphia morphia = new Morphia();
        for (String item : properties.getPackages()) {
            morphia.mapPackage(item);
        }
        return morphia;
    }

    @Bean
    public MongoClient mongoClient() {
        MongoClientURI mongoClientURI = new MongoClientURI(properties.getUri());
        return new MongoClient(mongoClientURI);
    }

    @Bean
    public Datastore datastore (Morphia morphia, MongoClient mongoClient) {
        Datastore datastore = morphia.createDatastore(mongoClient, properties.getDb());
        datastore.ensureIndexes();
        return datastore;
    }
}
