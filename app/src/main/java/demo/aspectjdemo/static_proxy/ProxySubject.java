package demo.aspectjdemo.static_proxy;

/**
 * Created by zhangxiaofei on 2017/5/3.
 */

public class ProxySubject implements Intention {

    Intention intention;
    public ProxySubject(Intention intention){
        this.intention = intention;
    }
    @Override
    public void doA() {
        System.out.println("doA called");
        intention.doA();
    }

    @Override
    public void doB() {
        System.out.println("doB called");
        intention.doB();

    }
}
