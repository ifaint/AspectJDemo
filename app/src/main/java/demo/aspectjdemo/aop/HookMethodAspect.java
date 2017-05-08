package demo.aspectjdemo.aop;

/**
 * Created by zhangxiaofei on 2017/5/3.
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


/**
 * Created by Tony Shen on 2016/12/7.
 */
@Aspect
public class HookMethodAspect {

    @Around("execution(!synthetic * *(..)) && onHookMethod()")
    public void doHookMethodd(final ProceedingJoinPoint joinPoint) throws Throwable {
        hookMethod(joinPoint);
    }

    @Pointcut("@within(demo.aspectjdemo.aop.HookMethod)||@annotation(demo.aspectjdemo.aop.HookMethod)")
    public void onHookMethod() {
    }

    private void hookMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        HookMethod hookMethod = method.getAnnotation(HookMethod.class);

        if (hookMethod==null) return;

        String beforeMethod = hookMethod.beforeMethod();
        String afterMethod = hookMethod.afterMethod();

        if (Preconditions.isNotBlank(beforeMethod)) {
            try {
                Reflect.on(joinPoint.getTarget()).call(beforeMethod);
            } catch (Exception e) {
                e.printStackTrace();
//                L.e("no method "+beforeMethod);
            }
        }

        joinPoint.proceed();

        if (Preconditions.isNotBlank(afterMethod)) {
            try {
                Reflect.on(joinPoint.getTarget()).call(afterMethod);
            } catch (Exception e) {
                e.printStackTrace();
//                L.e("no method "+afterMethod);
            }
        }
    }
}