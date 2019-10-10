package com.ninggc.util.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IUtilLogger {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    default void log(String content) {
        logger.info(content);
    }

    default void warn(String content) {
        logger.warn(content);
    }

    default void error(String content) {
        logger.error(content);
    }
}
