package com.ninggc.template.springbootfastdemo.common.config.aop.util;

import com.ninggc.template.springbootfastdemo.common.config.aop.AopConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IUtilLogger {
    Logger aopLogger = LogManager.getLogger(AopConfiguration.class);

    default void debug(String content) {
        aopLogger.debug("debug " + content);
    }

    default void info(String content) {
        aopLogger.info("info  " + content);
    }

    default void warn(String content) {
        aopLogger.warn("warn  " + content);
    }

    default void error(String content) {
        aopLogger.error("error " + content);
    }
}
