package demo.aspectjdemo.dynamicproxy;

/**
 * Created by zhangxiaofei on 2017/5/2.
 * Must have this interface for Proxy.newProxyInstance could not be cast to an implemented Class, such as SubjectA
 */
public interface AbstractSubject {
    void operate();

    void operateA(String values);
}
