package io.github.wesleyone.spring.circular.dependencies.beans;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanF {

    private BeanE e;

    public BeanE getE() {
        return e;
    }

    public void setE(BeanE e) {
        this.e = e;
    }
}
