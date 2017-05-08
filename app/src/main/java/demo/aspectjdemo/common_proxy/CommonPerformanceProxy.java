package demo.aspectjdemo.common_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import demo.aspectjdemo.aop.HookMethod;
import demo.aspectjdemo.dynamicproxy.AbstractSubject;

/**
 * Created by zhangxiaofei on 2017/5/3.
 */

public class CommonPerformanceProxy<T> implements InvocationHandler{

    T subject;


//    @HookMethod(beforeMethod = "invoke", afterMethod = "invoke")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long begin = System.currentTimeMillis();
        Object ret = method.invoke(subject,args);
        long duration = System.currentTimeMillis() - begin;
        System.out.println("[***dynamic]method:"+method.getName()+" cost:"+duration);
        return ret;
    }

    public T getInstance(T subject){
        this.subject = subject;
        return (T) Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(),this);
    }
}
