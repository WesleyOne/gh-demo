package io.github.wesleyone.spring.core.c1.c8;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanPostProcessorTest {

    @Test
    public void beanPostProcessorTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("1_8_1_BeanPostProcessor.xml");
        NormalBean normalBean = context.getBean("normalBean", NormalBean.class);
        System.out.println(normalBean);
    }
}
