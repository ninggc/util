package com.ninggc.template.springbootfastdemo.common.config.aop.util;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 只给aop用
 */
public interface IUtilLogger {
    Logger aopLogger = LogManager.getLogger(AopConfiguration.class);
    ThreadLocal<List<String>> currentThreadLogs = new ThreadLocal<List<String>>() {
        @Override
        protected List<String> initialValue() {
            return new ArrayList<>();
        }
    };
    ThreadLocal<Boolean> currentThreadNeedToLog = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    ThreadLocal<Throwable> currentThreadThrowable = new ThreadLocal<Throwable>() {
        @Override
        protected Throwable initialValue() {
            return super.initialValue();
        }
    };
    ThreadLocal<Integer> currentThreadServiceDepth = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    static void currentThreadServiceDepthAdd(int i) {
        if (currentThreadServiceDepth.get() == null) {
            currentThreadServiceDepth.set(0);
        }

        currentThreadServiceDepth.set(currentThreadServiceDepth.get() + i);
    }

    static void currentThreadServiceDepthCut(int i) {
        if (currentThreadServiceDepth.get() == null) {
            currentThreadServiceDepth.set(i);
        }

        currentThreadServiceDepth.set(currentThreadServiceDepth.get() - i);
    }

    /**
     * 将当前线程的日志存在map中
     *
     * @param content
     * @return
     */
    default List<String> tempSave(String content) {
        currentThreadLogs.get().add(content);
        return currentThreadLogs.get();
    }

    default void debug(String content) {
        aopLogger.debug(content);
        tempSave(content);
    }

    default void info(String content) {
        aopLogger.info(content);
        tempSave(content);
    }

    default void warn(String content) {
        aopLogger.warn(content);
        tempSave(content);
    }

    @Deprecated
    default void error(String content) {
        aopLogger.warn(content);
        tempSave(content);
        currentThreadNeedToLog.set(true);
    }

    default void error(String content, Throwable t) {
        aopLogger.warn(content);
        tempSave(content);
        currentThreadNeedToLog.set(true);
        currentThreadThrowable.set(t);
    }

    default void errorCurrentThread(String content) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            stringBuilder.append("=");
        }
        String split = stringBuilder.toString();
        if (currentThreadThrowable.get() != null) {
            aopLogger.error(split, currentThreadThrowable.get());
        }
        stringBuilder = new StringBuilder();
        for (String threadLog : currentThreadLogs.get()) {
            stringBuilder.append("\n").append("error ").append(threadLog);
        }
        aopLogger.error(stringBuilder.toString());
        aopLogger.error(split);

        currentThreadNeedToLog.remove();
        currentThreadLogs.set(new ArrayList<>());
    }
}
