package com.ninggc.template.springbootfastdemo.config;

public interface ILoggerInfoHandler {
//    自定义输出日志的标签
    String getTag();
//    自定义不发出警告的程序最大执行时间，单位ms，默认未300
    default Long getTimeThreshold() {
        return 300L;
    }
}
