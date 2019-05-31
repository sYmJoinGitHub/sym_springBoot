package com.sym.config;

import com.sym.component.SymComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 模拟：这个就是我们要自定义的自动配置类，由它来决定是否要把我们的组件<link>SymComponent</link>
 *      注入到springBoot的IOC容器中
 *
 * Created by 沈燕明 on 2019/5/30 16:37.
 */
@Configuration //标注它是一个配置类
@ConditionalOnWebApplication // 只有在web环境上，此配置类才会生效
@ConditionalOnProperty(prefix = "spring.sym",name = "component.enable",havingValue = "true") // 只有配置文件配置了spring.sym.component.enable=true才生效
// ...还可以有其它的判断条件
public class SymAutoConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(SymAutoConfiguration.class);

    @Bean(name = "symComponent")
    @ConditionalOnMissingBean(SymComponent.class) //如果标注在方法上，只对方法生效
    public SymComponent initSymComponent(){
        logger.info("环境适合,配置类SymAutoConfiguration已生效,开始注入组件SymComponent");
        return new SymComponent(110,"life is fantastic");
    }

}
