package demo.aspectjdemo.static_proxy;

/**
 * Created by zhangxiaofei on 2017/5/3.
 */

public class Subject implements Intention {
    @Override
    public void doA() {
        System.out.println("doA");
    }

    @Override
    public void doB() {
        System.out.println("doB");
    }
}
