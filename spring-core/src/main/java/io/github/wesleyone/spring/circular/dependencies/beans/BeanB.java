package io.github.wesleyone.spring.circular.dependencies.beans;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanB {

    private BeanA a;

    public BeanA getA() {
        return a;
    }

    public void setA(BeanA a) {
        this.a = a;
    }
}
