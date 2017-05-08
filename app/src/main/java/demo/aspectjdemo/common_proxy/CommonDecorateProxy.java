package demo.aspectjdemo.common_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by zhangxiaofei on 2017/5/3.
 * 动态代理,通用装饰器
 */

public class CommonDecorateProxy<T> implements InvocationHandler {
    T subject;


    public CommonDecorateProxy(){

    }
    public CommonDecorateProxy(T subject){
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        StringBuffer sb = new StringBuffer();
        sb.append("[***dynamic]"+method.getName()+"(");
        for(int i=0;args !=null&&i<args.length;i++){
            sb.append(args[i]);
        }
        Object ret = method.invoke(subject,args);
        sb.append(")--->"+ret);
        System.out.println(sb.toString());
        return ret;
    }

    public T getInstance(T obj){
        this.subject = obj;
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
    }
}
