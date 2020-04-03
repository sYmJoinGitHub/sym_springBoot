package com.sym.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author shenym
 * @date 2020/4/3 10:45
 */
@Component
public class SymEnvironmentResolver implements EnvironmentAware, CommandLineRunner {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(env.getProperty("sym.key"));
    }
}
