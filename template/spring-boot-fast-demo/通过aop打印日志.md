## 简述
平常我们打印日志的时候需要在每一个地方使用logger打印，aop提供了一种面向切面的方式，不需要在每一个地方都写一行代码，而是通过配置切面在我们需要执行的函数前后获得切点，在切点处直接执行相应的方法。话不多说，让我们一起来使用吧。

## 使用简述
本次使用的项目是基于Spring MVC的web项目，项目必须依赖spring，同时本项目基于注解配置了aop，当然你也可以xml配置aop。

## Maven配置

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

## 相关代码
AopConfiguration是核心，使用方式见ControllerAopConfigurationImpl文件
```Java
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
    protected Gson gson = new Gson();

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
```
ControllerAopConfigurationImpl是一个打印日志的示例，您只需要修改@Pointcut里面的内容，如下文中的实例`com.ninggc.template.springbootfastdemo.web.controller.*..*(..)`是指controller包下的所有类的所有方法，将其修改为自己的包即可。
```Java
/**
 * 控制controller的函数的入口和出口处打印日志
 */
@Component
@Aspect
public class ControllerAopConfigurationImpl extends AopConfiguration {
    @Pointcut("execution(* com.ninggc.template.springbootfastdemo.web.controller.*..*(..))")
    @Override
    protected void pointCutMethod() { }

    @Override
    public String getTag() {
        return "controller";
    }

    @Override
    public void before(JoinPoint joinPoint, String[] parameterNames, Object[] args) {
    }

    @Override
    public void afterReturn(JoinPoint joinPoint, Object returnValue) {
    }

    @Override
    public void afterThrow(JoinPoint joinPoint, Exception exception) throws Exception {
    }
}
```
其他的需要的文件
```Java
public interface IAopConfiguration {
    void before(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    void afterReturn(JoinPoint joinPoint, final Object returnValue);

    void afterThrow(JoinPoint joinPoint, Exception exception) throws Exception;
}
```
```Java
public interface ILoggerInfoHandler {
//    自定义输出日志的标签
    String getTag();
//    自定义不发出警告的程序最大执行时间，单位ms，默认未300
    default Long getTimeThreshold() {
        return 500L;
    }
}
```
以上四个文件是全部的文件，以下是具体的代码流程描述

## AopConfiguration使用详述
//第一类方法 wrap*方法
```
wrapBefore
wrapAfterReturn
wrapAfterThrow
wrapAround
//以上四个wrap*的方法是相应的切点处执行的方法，在这个实例中只在wrapAround上注解了@around，没有涉及到其他三个方法
```
//第二类方法 do*方法
```
doBefore
doAfterReturn
doAfterThrow
doAround
//wrap*方法调用了do*方法，do*方法是我打印日志的格式，内部调用了更具体的方法，见第三类方法
```
//第三类方法，这是继承类可以改写的方法，方法功能如方法名所示
```
public interface IAopConfiguration {
    void before(JoinPoint joinPoint, String[] parameterNames, Object[] args);

    void afterReturn(JoinPoint joinPoint, final Object returnValue);

    void afterThrow(JoinPoint joinPoint, Exception exception) throws Exception;
}
```
//打印日志的具体流程如下所示  
```
    private Object doAround(ProceedingJoinPoint joinPoint, String[] parameterNames, Object[] args) throws Throwable {
        String classAndMethodName = joinPoint.getSignature().toShortString();
        Object returnValue = null;

//        执行切点
        try {
            doBefore(joinPoint, parameterNames, args); //内部调用了before

            long start = System.currentTimeMillis();
            returnValue = joinPoint.proceed(args);
            long duration = System.currentTimeMillis() - start;
            if (duration > timeThreshold) {
                warn( classAndMethodName + " execute time is too long: " + " --> " + duration + " ms");
            } else {
                log(classAndMethodName + " execute time: " + " --> " + duration + " ms");
            }

            doAfterReturn(joinPoint, returnValue); //内部调用了afterReturn
        } catch (Exception e) {
            doAfterThrow(joinPoint, e); //内部调用了afterThrow
            throw e;
        }
        return joinPoint.proceed(args);
    }
```
