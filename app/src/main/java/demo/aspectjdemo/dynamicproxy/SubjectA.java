package demo.aspectjdemo.dynamicproxy;

/**
 * Created by zhangxiaofei on 2017/5/2.
 */

public class SubjectA implements AbstractSubject {

    @Override
    public void operate(){
        System.out.println("operate in subject");
    }
    @Override
    public void operateA(String values){
        System.out.println("operateA in subjectA:"+values);
    }
}
