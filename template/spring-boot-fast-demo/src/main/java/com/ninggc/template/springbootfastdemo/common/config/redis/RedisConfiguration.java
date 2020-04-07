package com.ninggc.template.springbootfastdemo.common.config.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    @Bean
    @ConditionalOnResource(resources = "classpath://application.properties")
    public RedisConnectionFactory redisConnectionFactory(RedisProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(properties.getHost());
        configuration.setPort(properties.getPort());
//        configuration.setDatabase(properties.getDatabase());
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    @ConditionalOnResource(resources = "classpath://application.properties")
    public Jedis jedis(RedisProperties properties) {
        return new Jedis(properties.getHost());
    }
}
