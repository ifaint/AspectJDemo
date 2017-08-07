package demo.aspectjdemo;

/**
 * Created by zhangxiaofei on 2017/8/3.
 */

public class MathKit
{
    static{
        System.loadLibrary("jnidemo");
    }
    // 定义native本地方法，和普通方法相同，加上native关键字
    public static native int square(int num);
}