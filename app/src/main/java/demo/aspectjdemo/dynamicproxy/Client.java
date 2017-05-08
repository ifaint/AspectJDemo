package demo.aspectjdemo.dynamicproxy;

import android.app.Activity;

import java.lang.reflect.Proxy;

import demo.aspectjdemo.aop.Trace;

/**
 * Created by zhangxiaofei on 2017/5/2.
 * Client could not access SubjectA directly for some reason: remote, heavy, change, security
 */

public class Client {

    SubjectA subject;
    Activity activity;

    public void oldInit(){
        subject = new SubjectA();
        subject.operate();
    }

    @Trace
    public void newInit(){
        subject = new SubjectA();

        //动态代理类的第一种生成方式
        TheProxy proxy = new TheProxy(new SubjectA());
        AbstractSubject sub = (AbstractSubject) Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(),proxy);
        sub.operateA("operation A 's values");

        //动态代理类的第二种生成方式,貌似更优一些
        AbstractSubject sub2 = (AbstractSubject) new TheProxy().newProxyInstance(new SubjectB());
        sub2.operateA("aaa");//这个有点问题

    }


}
