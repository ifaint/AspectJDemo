package demo.aspectjdemo.dynamicproxy;

/**
 * Created by zhangxiaofei on 2017/5/2.
 */

public class SubjectB implements AbstractSubject {
    @Override
    public void operate() {

    }

    @Override
    public void operateA(String values) {
        System.out.println("values is:"+values+" in SubjectB");

    }
}
