package com.ninggc.util.common.aop.adapter.impl;

import com.ninggc.util.common.aop.action.logger.IAopLoggerHandler;
import com.ninggc.util.common.aop.action.logger.IgnoredMethod;
import com.ninggc.util.common.aop.action.logger.TagEnum;
import com.ninggc.util.common.aop.adapter.IAopAdapter;
import com.ninggc.util.common.aop.adapter.anno.AopAdapter;
import com.ninggc.util.common.aop.util.IUtilGson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author ninggc
 */
@AopAdapter
@Scope("prototype")
public class LoggerAopAdapter implements IAopAdapter, IUtilGson {
    private IAopLoggerHandler aopLoggerHandler;

    @Override
    public void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        if (isController()) {
            // controller开始的时候清一下之前请求的信息
            currentThreadServiceDepth.set(0);
            currentThreadLogs.set(new ArrayList<>());
        }
        if (checkBeforeLog(joinPoint)) {
            return;
        }

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
        if (checkBeforeLog(joinPoint)) {
            return null;
        }

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
        if (isController()) {
            if (currentThreadNeedToLog.get()) {
                // 只在controller结束的时候打印
                errorCurrentThread(logContent.toString());
            }
        }
        return returnValue;
    }

    @Override
    public void doAfterThrow(JoinPoint joinPoint, Exception exception) {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        if (checkBeforeLog(joinPoint)) {
            return;
        }

        StringBuilder logContent = initLogContent();

        logContent.append("after  ")
                .append(aopLoggerHandler.getTag())
                .append(" throw:   ")
                .append(classAndMethodName)
                .append("\tthrow --> ")
                .append("\tmsg: ").append(exception.getMessage())
                .append("\tname: ").append(exception.getClass().getName());

        error(logContent.toString(), exception);
        if (isController() && currentThreadNeedToLog.get()) {
            // 只在controller结束的时候打印
            errorCurrentThread(logContent.toString());
        }
    }

    /**
     * @return true 跳过
     */
    private boolean checkBeforeLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(IgnoredMethod.class) != null;
    }

    @Override
    public void initAopAdapter(Object... objects) {
        Assert.isTrue(objects.length >= 1, "初始化" + this.getClass().getName() + "的参数不正确");
        this.aopLoggerHandler = (IAopLoggerHandler) objects[0];
    }

    /**
     * 在这里初始化打印日志的内容，不同类型的日志样式不同
     *
     * @return
     */
    private StringBuilder initLogContent() {
        StringBuilder logContent = new StringBuilder();
        // 不同类型的日志样式不同
        if (TagEnum.SERVICE.equals(aopLoggerHandler.getTag())) {
            for (int i = 0; i < currentThreadServiceDepth.get(); i++) {
                logContent.append("-— ");
            }
        } else if (isController()) {
            logContent.append("## ");
        } else {
            logContent.append("======== ");
        }

        return logContent;
    }

    private boolean isController() {
        return TagEnum.CONTROLLER.equals(aopLoggerHandler.getTag());
    }

    /**
     * 对需要打印的PO返回true
     * o 为空的逻辑在之前处理
     */
    protected boolean isLogIt(@NotNull Object o) {
        if (o.getClass().isInterface() || o.getClass().getName().contains("Impl")) {
            return false;
        }
        if (o.getClass().getName().contains("bigdata")) {
            return true;
        }
        // if (o instanceof Page) {
        //     return true;
        // }
        if (o instanceof List) {
            List list = (List) o;
            if (list.size() == 0) {
                return true;
            } else {
                return isLogIt(list.get(0));
            }
        }
        if (o instanceof Map) {
            Map map = (Map) o;
            if (map.size() == 0) {
                return true;
            } else {
                Iterator set = map.keySet().iterator();
                Iterator values = map.values().iterator();
                return isLogIt(set) && isLogIt(values);
            }
        }
        if (o instanceof Iterable) {
            Iterator iterator = ((Iterable) o).iterator();
            if (iterator.hasNext()) {
                // 递归处理，list类型可以打印，map类型暂时不打印
                return isLogIt(iterator.next());
            } else {
                return true;
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
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(LoggerAopAdapter.class);
    }
}
