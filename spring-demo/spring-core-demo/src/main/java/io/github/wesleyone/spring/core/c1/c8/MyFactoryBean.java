package io.github.wesleyone.spring.core.c1.c8;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author http://wesleyone.github.io/
 */
public class MyFactoryBean implements FactoryBean<NormalBean> {

    @Override
    public NormalBean getObject() throws Exception {
        NormalBean normalBean = new NormalBean();
        normalBean.setName("wesleyOne");
        return normalBean;
    }

    @Override
    public Class<?> getObjectType() {
        return NormalBean.class;
    }
}
