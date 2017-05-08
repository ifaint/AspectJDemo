package demo.aspectjdemo.common_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import demo.aspectjdemo.dynamicproxy.AbstractSubject;
import demo.aspectjdemo.dynamicproxy.SubjectA;

/**
 * Created by zhangxiaofei on 2017/5/3.
 */

public class Client {

    public void doSomething(){
        Set s = new HashSet();
        s.add("1");
        s.add("2");
        boolean has3 = s.contains("3");
        boolean has2 = s.contains("2");
    }

    public void doOtherthing(final Set set){
        Set proxyS = (Set) Proxy.newProxyInstance(Set.class.getClassLoader(), Set.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                StringBuffer sb = new StringBuffer();
                sb.append(method.getName()+"(");
                for(int i=0;args !=null&i<args.length;i++){
                    sb.append(args[i]);
                }
                Object ret = method.invoke(set,args);
                sb.append(")--->"+ret);
                System.out.println(sb.toString());
                return ret;
            }
        });
        proxyS.add("1");
        proxyS.contains("2");
        proxyS.add("3");
        proxyS.contains("4");
    }

    public void doOtherthing2(){
        Set s = (Set) new CommonDecorateProxy<>().getInstance(new HashSet());
        s.add("1");
        s.contains("2");
        s.add("3");
        s.contains("3");

    }

    public void doOtherthing3(){
        AbstractSubject subject = (AbstractSubject) new CommonDecorateProxy<>().getInstance(new SubjectA());
        subject.operateA("subjectA in common");
        subject.operate();

    }


}
