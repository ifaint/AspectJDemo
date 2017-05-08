package demo.aspectjdemo.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhangxiaofei on 2017/5/2.
 */

public class TheProxy implements InvocationHandler {
    AbstractSubject subject;

    public TheProxy(SubjectA subject){
        this.subject = subject;
    }
    public TheProxy(){

    }

    @Override
    /**
     * proxy:代理类实例
     * method:被调用方法引用
     * objects:调用方法参数
     */
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        System.out.println("[***dynamic]do before real invoke of method:"+method.getName()+" in :"+subject);
        return method.invoke(subject, objects[0]);
    }

    public Object newProxyInstance(AbstractSubject subject){
        this.subject = subject;
        return Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(),this);
    }
}
