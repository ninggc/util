package com.ninggc.template.springbootfastdemo.test.mybatis;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis - 通用分页插件（如果开启二级缓存需要注意）
 * 插件示例
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
@Slf4j
class PageHelper implements Interceptor {

    public static final ThreadLocal<Page> localPage = new ThreadLocal<>();

    /**
     * 开始分页
     *
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(int pageNum, int pageSize) {
        localPage.set(new Page(pageNum, pageSize));
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     *
     * @return
     */
    public static Page endPage() {
        Page page = localPage.get();
        localPage.remove();
        return page;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (localPage.get() == null) {
            return invocation.proceed();
        }
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个插件拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            //分页信息if (localPage.get() != null) {
            Page page = localPage.get();
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            // 分页参数作为参数对象parameterObject的一个属性
            String sql = boundSql.getSql();
            // 重写sql
            String pageSql = buildPageSql(sql, page);
            //重写分页sql
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            Connection connection = (Connection) invocation.getArgs()[0];
            // 重设分页参数里的总页数等
            setPageParameter(sql, connection, mappedStatement, boundSql, page);
            // 将执行权交给下一个插件
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
            Page page = localPage.get();
            page.setResult((List) result);
            return result;
        }
        return null;
    }

    /**
     * 只拦截这两种类型的
     * <br>StatementHandler
     * <br>ResultSetHandler
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 修改原SQL为分页SQL
     *
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(200);
        pageSql.append("select * from (");
        pageSql.append(sql);
        pageSql.append(" ) temp limit ").append(page.getStartRow());
        pageSql.append(" , ").append(page.getPageSize());
        return pageSql.toString();
    }

    /**
     * 获取总记录数
     *
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Page page) {
        // 记录总记录数
        String countSql = "select count(0) from (" + sql + ") temp";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotal(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setPages(totalPage);
        } catch (SQLException e) {
            log.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 代入参数值
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    @Data //采用lombok插件编译
    public static class Page<E> {
        private int pageNum;
        private int pageSize;
        private int startRow;
        private int endRow;
        private long total;
        private int pages;
        private List<E> result;

        public Page(int pageNum, int pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
            this.endRow = pageNum * pageSize;
        }

    }
}