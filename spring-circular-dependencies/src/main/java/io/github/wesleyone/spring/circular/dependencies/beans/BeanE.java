package io.github.wesleyone.spring.circular.dependencies.beans;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanE {

    private BeanF f;

    public BeanF getF() {
        return f;
    }

    public void setF(BeanF f) {
        this.f = f;
    }
}
