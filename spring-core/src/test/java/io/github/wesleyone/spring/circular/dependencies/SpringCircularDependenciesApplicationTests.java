package io.github.wesleyone.spring.circular.dependencies;

import io.github.wesleyone.spring.BootStrap;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanA;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanB;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanE;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanF;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;


public class SpringCircularDependenciesApplicationTests {

    /**
     * 两个单例循环依赖，正常
     */
    @Test
    public void test_spring_setter() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context-ok-singleton-singleton.xml");
        BeanA a = context.getBean("a", BeanA.class);
        BeanB b = context.getBean("b", BeanB.class);
        Assert.assertEquals(a, b.getA());
        Assert.assertEquals(b, a.getB());
    }

    /**
     * 一个单例和原型循环依赖，正常
     */
    @Test
    public void test_spring_singleton2prototype() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context-ok-singleton-prototype.xml");
        BeanE e = context.getBean("e", BeanE.class);
        BeanF f = context.getBean("f", BeanF.class);
        BeanF f2 = context.getBean("f", BeanF.class);
        Assert.assertEquals(e, f.getE());
        Assert.assertEquals(e, f2.getE());
        Assert.assertNotEquals(f, f2);
    }

    /**
     * 构造方法循环依赖，启动异常
     */
    @Test
    public void test_spring_constructor() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("context-err-constructor.xml");
        } catch (Throwable e) {
            do {
                if (e instanceof BeanCurrentlyInCreationException) {
                    e.printStackTrace();
                    return;
                }
                e = e.getCause();
            } while (e != null);
        }
        Assert.fail("循环依赖处理不支持构造器注入");
    }

    /**
     * 两个原型依赖循环，启动无异常
     * 获取原型对象时异常
     * PS:原型Bean默认不会初始化，而是使用时才会初始化
     */
    @Test
    public void test_spring_prototype2prototype() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("context-err-all-prototype.xml");
            context.getBean("e");
        } catch (Throwable e) {
            do {
                if (e instanceof BeanCurrentlyInCreationException) {
                    e.printStackTrace();
                    return;
                }
                e = e.getCause();
            } while (e != null);
        }
        Assert.fail("循环依赖处理不支持原型之间依赖");
    }


}
