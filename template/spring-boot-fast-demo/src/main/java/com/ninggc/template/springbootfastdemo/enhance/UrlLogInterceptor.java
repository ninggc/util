package com.ninggc.template.springbootfastdemo.enhance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ninggc
 */
public class UrlLogInterceptor implements HandlerInterceptor {
    public static final ThreadLocal<StringBuilder> curlBuilder = ThreadLocal.withInitial(StringBuilder::new);
    Logger logger = LoggerFactory.getLogger(getClass());
    ThreadLocal<Long> startTime = ThreadLocal.withInitial(() -> 0L);
    Map<String, String> ssoIdUsernameMap = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            startTime.set(System.currentTimeMillis());

            StringBuffer requestURLWithParams = request.getRequestURL();
            if (request.getQueryString() != null) {
                requestURLWithParams.append("?");
                requestURLWithParams.append(request.getQueryString());
            }

            String urlMsg = generateBlank(34) + "url: " + requestURLWithParams
                    + "\tmethod: " + request.getMethod();
            startTime.set(System.currentTimeMillis());

            // Log4j2Utils看 - url的相关信息
            logger.info(urlMsg);

            // 将请求转换为可使用的curl并打印
            curlBuilder.set(new StringBuilder());
            // for (int i = 0; i < 66; i++) {
            //     curlBuilder.get().append(" ");
            // }
            switch (request.getMethod()) {
                case "GET":
                    curlBuilder.get().append("curl   --location   --request  GET    \"").append(requestURLWithParams).append("\" ");
                    break;
                case "POST":
                    curlBuilder.get().append("curl   --location   --request  POST   \"").append(requestURLWithParams).append("\" ");
                    curlBuilder.get().append(" --header \"Content-Type: application/json\" ");
                    // curlBuilder.get().append(" --data-raw \"").append(requestBody.toString()).append("\" ");
                    break;
                case "DELETE":
                    curlBuilder.get().append("curl   --location   --request  DELETE \"").append(requestURLWithParams).append("\" ");
                    break;
                case "PUT":
                    curlBuilder.get().append("curl   --location   --request  PUT    \"").append(requestURLWithParams).append("\" ");
                    curlBuilder.get().append(" --header \"Content-Type: application/json\" ");
                    // curlBuilder.get().append(" --data-raw \"").append(requestBody.toString()).append("\" ");
                    break;
                default:
                    ;
            }

            if (request.getHeader("userId") != null) {
                curlBuilder.get().append(" --header " + "\"userId: ").append(request.getHeader("userId")).append("\" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private String generateBlank(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 打印curl
        // 控制台和logger看
        String userId = request.getHeader("userId");
        curlBuilder.get().append(" # username = ")
                .append(ssoIdUsernameMap.get(userId));

        String curlCommand = curlBuilder.get().toString();
        // response.setHeader("curlCommand", curlCommand);
        logger.info(curlCommand);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long duration = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        // 超过1s
        if (duration > 1000) {
            logger.error("执行时间大于1s, duration = {} ms, api = {}", duration, request.getRequestURL());
        }
    }

}
