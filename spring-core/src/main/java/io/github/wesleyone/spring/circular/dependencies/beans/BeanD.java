package io.github.wesleyone.spring.circular.dependencies.beans;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanD {

    private final BeanC c;

    public BeanD(BeanC c) {
        this.c = c;
    }

    public BeanC getC() {
        return c;
    }
}
