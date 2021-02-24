package io.github.wesleyone.spring.circular.dependencies;

import io.github.wesleyone.spring.circular.dependencies.beans.BeanA;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanB;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanE;
import io.github.wesleyone.spring.circular.dependencies.beans.BeanF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;


public class SpringCircularDependenciesApplicationTests {

    /**
     * 两个单例循环依赖，正常
     */
    @Test
    public void test_spring_setter() {
        HashSet<String> sources = new HashSet<>();
        sources.add("context-ok-singleton-singleton.xml");
        SpringApplication springApplication = new SpringApplication(SpringCircularDependenciesApplication.class);
        springApplication.setSources(sources);
        ConfigurableApplicationContext context = springApplication.run();
        BeanA a = context.getBean("a", BeanA.class);
        BeanB b = context.getBean("b", BeanB.class);
        Assertions.assertEquals(a, b.getA());
        Assertions.assertEquals(b, a.getB());
    }

    /**
     * 一个单例和原型循环依赖，正常
     */
    @Test
    public void test_spring_singleton2prototype() {
        HashSet<String> sources = new HashSet<>();
        sources.add("context-ok-singleton-prototype.xml");
        SpringApplication springApplication = new SpringApplication(SpringCircularDependenciesApplication.class);
        springApplication.setSources(sources);
        ConfigurableApplicationContext context = springApplication.run();
        BeanE e = context.getBean("e", BeanE.class);
        BeanF f = context.getBean("f", BeanF.class);
        BeanF f2 = context.getBean("f", BeanF.class);
        Assertions.assertEquals(e, f.getE());
        Assertions.assertEquals(e, f2.getE());
        Assertions.assertNotEquals(f, f2);
    }

    /**
     * 构造方法循环依赖，启动异常
     */
    @Test
    public void test_spring_constructor() {
        HashSet<String> sources = new HashSet<>();
        sources.add("context-err-constructor.xml");
        SpringApplication springApplication = new SpringApplication(SpringCircularDependenciesApplication.class);
        springApplication.setSources(sources);
        try {
            springApplication.run();
        } catch (Throwable e) {
            do {
                if (e instanceof BeanCurrentlyInCreationException) {
                    e.printStackTrace();
                    return;
                }
                e = e.getCause();
            } while (e != null);
        }
        Assertions.fail("循环依赖处理不支持构造器注入");
    }

    /**
     * 两个原型依赖循环，启动无异常
     * 获取原型对象时异常
     */
    @Test
    public void test_spring_prototype2prototype() {
        HashSet<String> sources = new HashSet<>();
        sources.add("context-err-all-prototype.xml");
        SpringApplication springApplication = new SpringApplication(SpringCircularDependenciesApplication.class);
        springApplication.setSources(sources);
        ConfigurableApplicationContext context = springApplication.run();
        try {
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
        Assertions.fail("循环依赖处理不支持原型之间依赖");
    }


}
