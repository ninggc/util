package com.ninggc.template.springbootfastdemo.config.aop;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 控制切面的自定义操作，
 * 使用方式如下（基于注解）：
 * 1. 重写pointCut方法并定义切点
 * 2. 重写对应方法方法进入执行内容
 * 使用方式如下（基于xml）：
 * 1. 继承该类
 * 2. 配置切点
 * 3. 配置wrap方法为对应的before、after等
 */
@SuppressWarnings("unused")
public abstract class AopConfiguration implements ILoggerInfoHandler, IAopConfiguration {
    //    不发出警告的程序最大执行时间，单位ms
    private long timeThreshold;
//    日志打印的标签
    private String tag;

    public AopConfiguration() {
        timeThreshold = getTimeThreshold();
        tag = getTag();
    }

    private Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Autowired
    protected Gson gson;

    /**
     *  需要重写并定义切点
     */
    protected abstract void pointCutMethod();

    @Data
    private static class AopResult {
        private String explain;
        private String type;
        private Integer totalSize;
        private Integer totalLength;
        private List<Object> subList;
    }

    /**
     * 包裹before函数
     */
//    @Before("pointCutMethod()")
    public final void wrapBefore(JoinPoint joinPoint) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        doBefore(joinPoint, parameterNames, args);
    }

    /**
     * 包裹afterReturn函数
     */
//    @AfterReturning(value = "pointCutMethod()", returning = "returnValue")
    public final Object wrapAfterReturn(JoinPoint joinPoint, Object returnValue) {
        return doAfterReturn(joinPoint, returnValue);
    }

    /**
     * 包裹afterThrow函数
     */
//    @AfterThrowing(value = "pointCutMethod()", throwing = "exception")
    public final void wrapAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        doAfterThrow(joinPoint, exception);
    }

    /**
     * 包裹around函数
     * @return
     */
//    @Around(value = "pointCutMethod() && @annotation(methodLog)", argNames = "joinPoint")
    @Around(value = "pointCutMethod()", argNames = "joinPoint")
    private Object wrapAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        return doAround(joinPoint, parameterNames, args);
    }

    private void doBefore(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
        before(joinPoint, parameterNames, args);
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "before " + tag + " execute: " + classAndMethodName + "\t" +
                gson.toJson(parameterNames) + " --> " + gson.toJson(args);
        log(logContent);
    }

    private Object doAfterReturn(JoinPoint joinPoint, final Object returnValue) {
        afterReturn(joinPoint, returnValue);
        String classAndMethodName = joinPoint.getSignature().toShortString();

        String logContent = "after " + tag + " execute: " + classAndMethodName + "\t"
                + "result --> " + gson.toJson(getResultToAopResult(returnValue));
        log(logContent);
        return returnValue;
    }

    private void doAfterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
        afterThrow(joinPoint, exception);
        String classAndMethodName = joinPoint.getSignature().toShortString();
        String logContent = "after " + tag + " execute: " + classAndMethodName + "\t"
                + "throw --> " + gson.toJson(exception.getMessage());
        log(logContent);
        throw exception;
    }

    private Object doAround(ProceedingJoinPoint joinPoint, String[] parameterNames, Object[] args) throws Throwable {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        Object returnValue = null;

//        执行切点
        try {
            doBefore(joinPoint, parameterNames, args);

            long start = System.currentTimeMillis();
            returnValue = joinPoint.proceed(args);
            long duration = System.currentTimeMillis() - start;
            if (duration > timeThreshold) {
                warn( classAndMethodName + " execute time is too long: " + " --> " + duration + " ms");
            } else {
                log(classAndMethodName + " execute time: " + " --> " + duration + " ms");
            }

            doAfterReturn(joinPoint, returnValue);
        } catch (Exception e) {
            doAfterThrow(joinPoint, e);
            afterThrow(joinPoint, e);
            throw e;
        }
        return joinPoint.proceed(args);
    }

    /**
     * 如果结果是非常长的list，就要截取一部分打印到日志
     * @param resultValue
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Object getResultToAopResult(final Object resultValue) {
//        如果结果太长默认只取三条
        final int maxSize = 3;
        final int maxLength = 300;
        AopResult aopResult = new AopResult();
        if (resultValue instanceof Collection) {
            Collection<Object> collection = (Collection<Object>) resultValue;
            int length = gson.toJson(collection).length();
            if (collection.size() > maxSize && length > maxLength) {
//                如果结果的长度大于maxSize，并且字符串长度大于maxLength
//                就取出其中的maxSize条数据打印在日志
                aopResult.setType(resultValue.getClass().getSimpleName());
                aopResult.setExplain("截取" + maxSize + "条结果展示！");
                aopResult.setTotalSize(collection.size());
                aopResult.setTotalLength(length);
                aopResult.setSubList(Arrays.asList(collection.toArray()).subList(0, maxSize));
                return aopResult;
            }
        }
        return resultValue;
    }

    protected void log(String content) {
        logger.info(content);
    }

    protected void warn(String content) {
        logger.warn(content);
    }

    protected void error(String content) {
        logger.error(content);
    }


}
