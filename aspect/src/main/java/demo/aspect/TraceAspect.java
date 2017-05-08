package demo.aspect;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 截获类名最后含有Activity、Layout的类的所有非static方法（static方法另外加一个static修饰的execution或者call即可：execution( static * *..Activity+.*(..)）
 * 监听目标方法的执行时间
 */
@Aspect
public class TraceAspect {
    private static Object currentObject = null;
    //截获所有后缀为Activity或者Layout的类中所有方法的执行体（除了static，要监听static需要重新加一个static的execution 规则）
    //target、this是用于截获运行时类型，便于做一些入参、出参的修改，或者做其他操作
    private static final String POINTCUT_METHOD =
            "(execution(* *..AnotherActivity+.on*(..)) ||execution(* *..Layout+.*(..))) && target(Object) && this(Object)";

    //截获所有后缀为Activity或者Layout的类中所有方法的调用（除了static，要监听static需要重新加一个static的execution 规则）
    //target、this是用于截获运行时类型，便于做一些入参、出参的修改，或者做其他操作
    private static final String POINTCUT_CALL = "(call(* *..AnotherActivity+.on*(..)) || call(* *..Layout+.*(..))) && target(Object) && this(Object)";
    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotated() {}
    @Pointcut(POINTCUT_CALL)
    public void methodCall(){}

    /**
     *  截获原方法，并替换
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodAnnotated()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (currentObject == null){
            currentObject = joinPoint.getTarget();
        }
        //初始化计时器
        final StopWatch stopWatch = new StopWatch();
        //开始监听
        stopWatch.start();
        //调用原方法的执行。
        Object result = joinPoint.proceed();
        //监听结束
        stopWatch.stop();
        //获取方法信息对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className;
        //获取当前对象，通过反射获取类别详细信息
        className = joinPoint.getThis().getClass().getName();

        String methodName = methodSignature.getName();
        String msg =  buildLogMessage(methodName, stopWatch.getTotalTimeMillis());
        if (currentObject != null && currentObject.equals(joinPoint.getTarget())){
            DebugLog.log(className,msg);
        }else if(currentObject != null && !currentObject.equals(joinPoint.getTarget())){
            DebugLog.log(className, msg);
            Log.e(className,msg);
            currentObject = joinPoint.getTarget();
//        DebugLog.outPut(new Path());    //日志存储
//        DebugLog.ReadIn(new Path());    //日志读取
        }
        return result;
    }

    @After("methodCall()")
    public void onCallAfter(JoinPoint joinPoint) throws Throwable{
        Log.e("onCallAfter:", "class : "+joinPoint.getSignature().getDeclaringTypeName() + "method : " +((MethodSignature)joinPoint.getSignature()).getName());
    }
    /**
     * 在截获的目标方法调用之前执行该Advise
     * @param joinPoint
     * @throws Throwable
     */
    @Before("methodCall()")
    public void onCallBefore(JoinPoint joinPoint) throws Throwable{
        Log.e("onCallBefore:", "class : "+joinPoint.getSignature().getDeclaringTypeName() + "method : " +((MethodSignature)joinPoint.getSignature()).getName());
        Activity activity = null;
        //获取目标对象，截获运行时类型
        activity = ((Activity)joinPoint.getTarget());
        //插入自己的实现，控制目标对象的执行

        //做其他的操作
        buildLogMessage("test","20");
    }
    /**
     * 创建一个日志信息
     *
     * @param methodName 方法名
     * @param methodDuration 执行时间
     * @return
     */
    private static String buildLogMessage(String methodName, String methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
            message.append("ms");
        message.append("]      \n");
        return message.toString();
    }

}
