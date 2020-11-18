package com.ninggc.template.springbootfastdemo;

import com.google.gson.Gson;
import com.ninggc.template.springbootfastdemo.project.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class AppTest extends BaseTypeHandler<User> {
    private Gson gson = new Gson();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, User user, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, gson.toJson(user));
    }

    @Override
    public User getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
        // (User) resultSet.getString(s);
    }

    @Override
    public User getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public User getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

    public static void main(String[] args) throws IOException {
//        CacheBuilder.newBuilder()
//                .concurrencyLevel(8)
//                .initialCapacity(8)
////                .reS
//                .expireAfterWrite(6, TimeUnit.SECONDS)
//                .removalListener(new RemovalListener<Object, Object>() {
//                    @Override
//                    public void onRemoval(RemovalNotification<Object, Object> notification) {
//
//                    }
//                })
//                .build(new CacheLoader<Object, Object>() {
//                    @Override
//                    public Object load(Object key) throws Exception {
//                        return null;
//                    }
//                });

        String resource = "org/mybatis/builder/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);
    }
}
