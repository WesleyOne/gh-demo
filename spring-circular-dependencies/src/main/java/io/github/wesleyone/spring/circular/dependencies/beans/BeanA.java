package io.github.wesleyone.spring.circular.dependencies.beans;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanA {

    private BeanB b;

    public BeanB getB() {
        return b;
    }

    public void setB(BeanB b) {
        this.b = b;
    }
}
