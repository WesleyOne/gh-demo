package io.github.wesleyone.spring.circular.dependencies.beans;

/**
 * @author http://wesleyone.github.io/
 */
public class BeanC {

    private final BeanD d;

    public BeanC(BeanD d) {
        this.d = d;
    }

    public BeanD getD() {
        return d;
    }
}
