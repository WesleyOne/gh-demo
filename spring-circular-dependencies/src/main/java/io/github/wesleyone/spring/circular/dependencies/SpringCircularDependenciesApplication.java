package io.github.wesleyone.spring.circular.dependencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wesleyone
 */
@SpringBootApplication
public class SpringCircularDependenciesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringCircularDependenciesApplication.class, args);
    }

}
