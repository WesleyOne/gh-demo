package io.github.wesleyone.spring;

import io.github.wesleyone.spring.autowire.candidate.AutoWireBeanA;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author http://wesleyone.github.io/
 */
public class BootStrap {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("example1.xml", "example2.xml");

        AutoWireBeanA autoWireBeanA = context.getBean(AutoWireBeanA.class);
        System.out.println(autoWireBeanA.getAutoWireBeanB());
    }
}
