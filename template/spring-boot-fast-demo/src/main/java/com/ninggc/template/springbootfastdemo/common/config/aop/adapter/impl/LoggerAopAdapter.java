package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.logger.TagEnum;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilGson;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AopAdapter
@Scope("prototype")
public class LoggerAopAdapter implements IAopAdapter, IUtilGson {
    private IAopLoggerHandler aopLoggerHandler;

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        String classAndMethodName = joinPoint.getSignature().toShortString();

        StringBuilder logContent = initLogContent();

        logContent.append("before ")
                .append(aopLoggerHandler.getTag())
                .append(" execute: ")
                .append(classAndMethodName)
                .append("\tparams --> ");
        for (int i = 0; i < args.length; i++) {
            logContent.append("{")
                    .append(gson.toJson(parameterNames[i]))
                    .append(": ");
            // region 调整参数打印的格式
            String argsShow;
            if (args[i] == null) {
                argsShow = "null";
            } else if (!isLogIt(args[i])) {
                argsShow = "不打印";
            } else {
                try {
                    argsShow = gson.toJson(args[i]);
                } catch (Exception e) {
                    argsShow = "无法打印";
                    warn(parameterNames[i] + "无法打印: " + e.getMessage());
                }
            }
            logContent.append(argsShow);
            // endregion
            logContent.append("} ");
        }

        info(logContent.toString());
    }

    @Override
    public Object doAfterReturn(JoinPoint joinPoint, Object returnValue) {
        String classAndMethodName = joinPoint.getSignature().toShortString();

        StringBuilder logContent = initLogContent();

        logContent.append("after  ")
                .append(aopLoggerHandler.getTag())
                .append(" execute: ")
                .append(classAndMethodName);

        // region 调整参数打印的格式
        String argsShow;
        if (returnValue == null) {
            argsShow = "null";
        } else if (!isLogIt(returnValue)) {
            argsShow = "不打印";
        } else {
            try {
                argsShow = gson.toJson(returnValue);
            } catch (Exception e) {
                argsShow = "无法打印";
                warn("返回值无法打印: " + e.getMessage());
            }
        }
        logContent.append("\tresult --> ")
                .append(argsShow);
        // endregion
        info(logContent.toString());
        return returnValue;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {
        String classAndMethodName = joinPoint.getSignature().toShortString();

        StringBuilder logContent = initLogContent();

        logContent.append("after  ")
                .append(aopLoggerHandler.getTag())
                .append(" throw:   ")
                .append(classAndMethodName)
                .append("\tthrow --> ")
                .append(gson.toJson(exception.getMessage()));

        error(logContent.toString());
    }

    @Override
    public void initAopAdapter(Object... objects) {
        Assert.isTrue(objects.length == 1, "初始化" + this.getClass().getName() + "的参数不正确");
        this.aopLoggerHandler = (IAopLoggerHandler) objects[0];
    }

    /**
     * 在这里初始化打印日志的内容，不同类型的日志样式不同
     *
     * @return
     */
    private StringBuilder initLogContent() {
        StringBuilder logContent = new StringBuilder();
        logContent.append("thread_id: ")
                .append(Thread.currentThread().getId())
                .append(" ");

        // 不同类型的日志样式不同
        if (aopLoggerHandler.getTag().equals(TagEnum.SERVICE.getValue())) {
            logContent.append("————— ");
        } else if (aopLoggerHandler.getTag().equals(TagEnum.CONTROLLER.getValue())) {
            logContent.append("## ");
        } else {
            logContent.append("======== ");
        }

        return logContent;
    }

    /**
     * 对需要打印的PO返回true
     */
    protected boolean isLogIt(Object o) {
        if (o.getClass().getName().contains("bigdata")) {
            return true;
        }
        if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            } else {
                Object next = ((Collection) o).iterator().next();
                return isLogIt(next);
            }
        }
        return o.getClass().getName().contains("java.lang");
    }

    /**
     * 因为LoggerAopAdapter不是单例，所以重写equal
     * 防止装配adapter时装入多个loggerAdapter
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(LoggerAopAdapter.class);
    }
}
