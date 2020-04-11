package com.sym.config;

import com.sym.component.SymTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 导入自定义组件的自动配置类。
 * 可以通过自定义注解来导入,
 * 可以通过各种 @ConditionalXXX 注解来指定生效条件,
 * 其它情况
 *
 * @author ym.shen
 * Created on 2020/4/11 11:02
 */
//也可以不使用自定义注解, 改为使用spring的注解条件方式来让此配置类生效
//@Configuration
//@ConditionalOnProperty(prefix = "spring.sym",name = "component.enable",havingValue = "true")
//@ConditionalOnWebApplication
public class SymTemplateAutoConfiguration {

    /**
     * 当配置类生效时, 此方法便可以注册组件到 ioc 容器中
     * @return 自定义组件
     */
    @Bean
    @ConditionalOnMissingBean(SymTemplate.class)
    public SymTemplate symTemplate(){
        return new SymTemplate();
    }
}
