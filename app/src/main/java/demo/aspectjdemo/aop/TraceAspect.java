package demo.aspectjdemo.aop;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Tony Shen on 16/3/22.
 */
@Aspect
public class TraceAspect {

//    @Around("execution(!synthetic * *(..)) && onTraceMethod()")
    @Around("execution(* demo.aspectjdemo.MainActivity.on**(..))")
    public Object doTraceMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        return traceMethod(joinPoint);
    }

//    @Pointcut("@within(demo.aspectjdemo.aop.Trace)||@annotation(demo.aspectjdemo.aop.Trace)")
//    public void onTraceMethod() {
//    }

    private Object traceMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        System.out.println(buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));

        return result;
    }

    /**
     * Create a log message.
     *
     * @param methodName A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("**aop_trace**");
        message.append(methodName);
        message.append("()");
        message.append(" take ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }
}
