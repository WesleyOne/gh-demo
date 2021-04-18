package io.github.wesleyone.spring.core.c1.c8;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;

/**
 * @author http://wesleyone.github.io/
 */
public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private NotEligibleBean notEligibleBean;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (notEligibleBean != null) {

        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Bean '" + beanName + "' created : " + bean.toString());
        return bean;
    }
}
