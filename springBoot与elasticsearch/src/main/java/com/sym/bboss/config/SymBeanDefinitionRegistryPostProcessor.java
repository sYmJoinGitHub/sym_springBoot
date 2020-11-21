package com.sym.bboss.config;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * BeanDefinitionRegistry后置处理器，用它来执行自定义的类扫描器{@link BbossClassPathBeanDefinitionScanner}
 * <p>
 * Created by shenYm on 2019/8/17.
 */
public class SymBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private String[] basePackages;

    public SymBeanDefinitionRegistryPostProcessor(){
        this.basePackages = "com.sym.mapper".split(",");
    }

    public SymBeanDefinitionRegistryPostProcessor(String[] basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (!ArrayUtils.isEmpty(basePackages)) {
            BbossClassPathBeanDefinitionScanner scanner = new BbossClassPathBeanDefinitionScanner(registry);
            scanner.scan(basePackages);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }
}
