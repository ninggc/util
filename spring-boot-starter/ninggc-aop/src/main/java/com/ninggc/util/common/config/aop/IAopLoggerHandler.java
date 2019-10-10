package com.ninggc.util.common.config.aop;

import com.ninggc.util.common.util.IUtilGson;

import java.util.Arrays;
import java.util.Collection;

public interface IAopLoggerHandler extends IUtilGson {
//    自定义输出日志的标签
    String getTag();
    //    自定义不发出警告的程序最大执行时间，单位ms，默认未300
    default Long getTimeThreshold() {
        return 500L;
    }

    /**
     * 如果结果是非常长的list，就要截取一部分打印到日志
     * @param resultValue
     * @return
     */
    @SuppressWarnings("unchecked")
    default Object getResultToAopResult(final Object resultValue) {
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

}
