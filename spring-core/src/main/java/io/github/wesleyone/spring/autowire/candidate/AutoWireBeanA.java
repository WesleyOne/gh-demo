package io.github.wesleyone.spring.autowire.candidate;

/**
 * @author http://wesleyone.github.io/
 */
public class AutoWireBeanA {

//    @Autowired
    private AutoWireBeanB autoWireBeanB;

    public AutoWireBeanB getAutoWireBeanB() {
        return autoWireBeanB;
    }

    public void setAutoWireBeanB(AutoWireBeanB autoWireBeanB) {
        this.autoWireBeanB = autoWireBeanB;
    }
}
