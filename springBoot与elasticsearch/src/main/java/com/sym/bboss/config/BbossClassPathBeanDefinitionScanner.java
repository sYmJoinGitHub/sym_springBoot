package com.sym.bboss.config;

import com.sym.bboss.annotation.BbossMapper;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 自定义扫描器用来扫描到{@link BbossMapper}注解的接口
 * 默认spring是不会去扫描到接口的
 * <p>
 * Created by shenYm on 2019/8/8.
 */
public class BbossClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public BbossClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        // 注册过滤器
        addIncludeFilter(new AnnotationTypeFilter(BbossMapper.class));
    }


    /**
     * 由于我们在构造方法以及限制死过滤出带有{@link BbossMapper}的类，所以这边只需要针对该注解做处理即可
     *
     * @param beanDefinition
     * @return
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface() && metadata.isIndependent();
    }


}
