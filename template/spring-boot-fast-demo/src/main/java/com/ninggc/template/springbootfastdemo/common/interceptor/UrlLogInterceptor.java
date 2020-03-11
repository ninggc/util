package com.ninggc.template.springbootfastdemo.common.interceptor;

import com.ninggc.template.springbootfastdemo.common.config.aop.util.IUtilLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UrlLogInterceptor implements HandlerInterceptor, IUtilLogger {
    public static ThreadLocal<StringBuilder> curlBuilder = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder();
        }
    };
    Logger logger = LogManager.getLogger(UrlLogInterceptor.class);
    ThreadLocal<Long> startTime = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return 0L;
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            StringBuffer requestURLWithParams = request.getRequestURL();
            if (request.getQueryString() != null) {
                requestURLWithParams.append("?");
                requestURLWithParams.append(request.getQueryString());
            }
            String urlMsg = "thread_id: " + Thread.currentThread().getId()
                    + "\turl: " + requestURLWithParams
                    + "\tmethod: " + request.getMethod();
            startTime.set(System.currentTimeMillis());

            // 将请求转换为可使用的curl并打印
            curlBuilder.set(new StringBuilder().append(urlMsg).append("\n"));
            switch (request.getMethod()) {
                case "GET":
                    curlBuilder.get().append("curl --location --request GET '").append(requestURLWithParams).append("' ");
                    break;
                case "POST":
                    curlBuilder.get().append("curl --location --request POST '").append(requestURLWithParams).append("' ");
                    curlBuilder.get().append(" --header 'Content-Type: application/json' ");
                    // curlBuilder.get().append(" --data-raw '").append(requestBody.toString()).append("' ");
                    break;
                case "DELETE":
                    curlBuilder.get().append("curl --location --request DELETE '").append(requestURLWithParams).append("' ");
                    break;
                case "PUT":
                    curlBuilder.get().append("curl --location --request PUT '").append(requestURLWithParams).append("' ");
                    curlBuilder.get().append(" --header 'Content-Type: application/json' ");
                    // curlBuilder.get().append(" --data-raw '").append(requestBody.toString()).append("' ");
                    break;
                default:
            }

            if (request.getHeader("userId") != null) {
                curlBuilder.get().append(" --header " + "'userId: ").append(request.getHeader("userId")).append("' ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 打印执行时间
        // long executeTime = System.currentTimeMillis() - startTime.get();
        // if (executeTime < 500) {
        //     return;
        // }
        // String urlMsg = "thread_id: " + Thread.currentThread().getId()
        //         + "\turl: " + request.getRequestURI()
        //         + "\tmethod: " + request.getMethod()
        //         + "\texecuteTime: " + executeTime + "ms";
        // logger.info(urlMsg);

        // 打印curl
        // 控制台和logger看
        logger.info(curlBuilder.get().toString());
        info(curlBuilder.get().toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
