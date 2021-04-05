package io.github.wesleyone.spring.autowire.candidate;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author http://wesleyone.github.io/
 */
@Component
@Configurable(autowire = Autowire.BY_TYPE)
public class BeanConfig {

    @Bean
    public AutoWireBeanB autoWireBeanB() {
        AutoWireBeanB autoWireBeanB = new AutoWireBeanB();
        autoWireBeanB.setData("bbb");
        return autoWireBeanB;
    }

    @Bean(autowire = Autowire.BY_TYPE)
    public AutoWireBeanA autoWireBeanA() {
        AutoWireBeanA autoWireBeanA = new AutoWireBeanA();
        return autoWireBeanA;
    }
}
