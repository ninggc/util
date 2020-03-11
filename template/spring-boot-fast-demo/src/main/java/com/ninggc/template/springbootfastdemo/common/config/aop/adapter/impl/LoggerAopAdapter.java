package com.ninggc.template.springbootfastdemo.common.config.aop.adapter.impl;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.logger.IAopLoggerHandler;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.logger.IgnoredMethod;
import com.ninggc.template.springbootfastdemo.common.config.aop.action.logger.TagEnum;
import com.ninggc.template.springbootfastdemo.common.config.aop.adapter.IAopAdapter;
import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilGson;
import com.ninggc.template.springbootfastdemo.common.interceptor.UrlLogInterceptor;
import com.ninggc.util.morphia.util.Page;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

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
        if (aopLoggerHandler.getTag().equals(TagEnum.CONTROLLER.getValue())) {
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

        // 找到方法上标注了@RequestBody注解的字段
        Integer foWithRequestBody = -1;
        if (TagEnum.CONTROLLER.getValue().equals(aopLoggerHandler.getTag())) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            if (signature.getMethod().getAnnotation(PostMapping.class) != null) {
                for (int i1 = 0; i1 < signature.getMethod().getParameterAnnotations().length; i1++) {
                    for (Annotation annotation : signature.getMethod().getParameterAnnotations()[i1]) {
                        if (annotation instanceof RequestBody) {
                            foWithRequestBody = i1;
                        }
                    }
                }
            }
        }

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
                    if (foWithRequestBody.equals(i)) {
                        // 将标注了@RequestBody的字段的值给到拦截器上
                        StringBuilder curlBuilder = UrlLogInterceptor.curlBuilder.get();
                        if (curlBuilder != null) {
                            curlBuilder.append(" --data-raw '").append(argsShow).append("' ");
                            info(curlBuilder.toString());
                        }
                    }
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
            return returnValue;
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
        if (aopLoggerHandler.getTag().equals(TagEnum.CONTROLLER.getValue())) {
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
        if (aopLoggerHandler.getTag().equals(TagEnum.CONTROLLER.getValue()) && currentThreadNeedToLog.get()) {
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
        // 不同类型的日志样式不同
        if (aopLoggerHandler.getTag().equals(TagEnum.SERVICE.getValue())) {
            for (int i = 0; i < currentThreadServiceDepth.get(); i++) {
                logContent.append("-— ");
            }
        } else if (aopLoggerHandler.getTag().equals(TagEnum.CONTROLLER.getValue())) {
            logContent.append("## ");
        } else {
            logContent.append("======== ");
        }

        return logContent;
    }

    /**
     * 对需要打印的PO返回true
     * o 为空的逻辑在之前处理
     */
    protected boolean isLogIt(@NotNull Object o) {
        if (o.getClass().getName().contains("bigdata")) {
            return true;
        }
        if (o instanceof Page) {
            return true;
        }
        if (o instanceof Iterable) {
            Iterator iterator = ((Iterable) o).iterator();
            if (iterator.hasNext()) {
                // 递归处理，list类型可以打印，map类型暂时不打印
                isLogIt(iterator.next());
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
