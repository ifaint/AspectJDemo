package demo.aspectjdemo;


import junit.framework.TestSuite;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import demo.aspectjdemo.aop.Trace;
import demo.aspectjdemo.common_proxy.CommonDecorateProxy;
import demo.aspectjdemo.common_proxy.CommonPerformanceProxy;
import demo.aspectjdemo.dynamicproxy.AbstractSubject;
import demo.aspectjdemo.dynamicproxy.Client;
import demo.aspectjdemo.dynamicproxy.SubjectA;
import demo.aspectjdemo.dynamicproxy.SubjectB;
import demo.aspectjdemo.dynamicproxy.TheProxy;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Suite.SuiteClasses(TestSuite.class)
public class ExampleUnitTest implements TestAble{
    @Test
    @Ignore
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDynamicProxy(){
        AbstractSubject subject = new SubjectA();

        //动态代理类的第一种生成方式
        TheProxy proxy = new TheProxy(new SubjectA());
        AbstractSubject sub = (AbstractSubject) Proxy.newProxyInstance(subject.getClass().getClassLoader(),subject.getClass().getInterfaces(),proxy);
        sub.operateA("operation A 's values");

        //动态代理类的第二种生成方式,貌似更优一些
        AbstractSubject sub2 = (AbstractSubject) new TheProxy().newProxyInstance(new SubjectB());
        sub2.operateA("aaa");//这个有点问题
    }

    @Test
    public void testCommonProxy(){
        AbstractSubject subject = (AbstractSubject) new CommonDecorateProxy<>().getInstance(new SubjectA());
        subject.operateA("subjectA in common");
        subject.operate();
    }

    @Test
    public void testPerformanceProxy(){
        CommonPerformanceProxy<Set> proxy = new CommonPerformanceProxy<Set>();
        Set s = proxy.getInstance(new HashSet<>());
        s.add("one");
        s.add("two");
        s.contains("one");
        s.contains("three");
    }

    @Test
    public void testSelf(){
        TestAble test = new ExampleUnitTest();
        TestAble proxyTest = (TestAble) new CommonPerformanceProxy<>().getInstance(test);
        proxyTest.test();
    }

    @Override
    public void test() {
        testDynamicProxy();
        testCommonProxy();
        testPerformanceProxy();
        Client client = new Client();
        client.newInit();
    }
}