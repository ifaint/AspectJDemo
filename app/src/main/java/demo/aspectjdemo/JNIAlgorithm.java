package demo.aspectjdemo;

/**
 * Created by zhangxiaofei on 2017/9/6.
 */

public class JNIAlgorithm {

    static{
        System.loadLibrary("jnidemo");
    }
    public static native int sum(int n1, int n2);
}
