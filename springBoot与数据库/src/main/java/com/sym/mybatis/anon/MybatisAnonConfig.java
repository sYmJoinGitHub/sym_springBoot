package com.sym.mybatis.anon;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springBoot与mybatis整合-注解版，对mybatis的额外配置
 *
 * Created by 沈燕明 on 2018/11/10.
 */
@Configuration
public class MybatisAnonConfig {


    /**
     * 通过springBoot提供的ConfigurationCustomizer(定制器)，可以向springBoot自动配置mybatis的组件
     * 增加额外的配置。
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // 开启数据库字段名 与 JavaBean驼峰命名 的匹配规则
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
