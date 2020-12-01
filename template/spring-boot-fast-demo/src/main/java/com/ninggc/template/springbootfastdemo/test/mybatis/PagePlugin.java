package com.ninggc.template.springbootfastdemo.test.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;

/**
 * @see org.apache.ibatis.plugin.InterceptorChain#pluginAll(Object)
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PagePlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget(); //被代理对象
        Method method = invocation.getMethod(); //代理方法
        Object[] args = invocation.getArgs(); //方法参数
        // do something ...... 方法拦截前执行代码块
        Object result = invocation.proceed();
        // do something .......方法拦截后执行代码块
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            // 只处理Executor
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public static void main(String[] args) {
        PagePlugin pagePlugin = new PagePlugin();
        Executor plugin = (Executor) pagePlugin.plugin(new SimpleExecutor(null, null));
        // plugin.
    }
}
