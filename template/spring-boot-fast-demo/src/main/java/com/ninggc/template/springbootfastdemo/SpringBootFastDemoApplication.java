package com.ninggc.template.springbootfastdemo;

import com.ninggc.template.springbootfastdemo.project.dao.SmsRecordMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ninggc
 */
@SpringBootApplication
//@PropertySource("classpath:morphia.properties")
@EnableSwagger2
@MapperScan("com.ninggc.template.springbootfastdemo.project.dao")
@EnableTransactionManagement
@RestController
//@EnableDiscoveryClient
public class SpringBootFastDemoApplication implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    AbstractApplicationContext context;

    // @Autowired
    // UserMapper userMapper;
    @Autowired
    SmsRecordMapper smsRecordMapper;
    @Autowired
    SqlSession sqlSession;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) {
        // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        SpringApplication.run(SpringBootFastDemoApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
//        smsRecordMapper.selectById(3);
//        smsRecordMapper.selectById(3);
        System.out.println("onApplicationEvent");
//        AopContext.currentProxy();
        TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        transactionTemplate.execute(transactionStatus -> {
            jdbcTemplate.query("select * from `turing-test`.bd_sms_record limit 1", (resultSet, i) -> {
                return null;
            });
            return null;
        });
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
