package com.sym.annotation;

import com.sym.config.SymTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以自定义一个注解
 *
 * @author ym.shen
 * Created on 2020/4/11 10:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
// 通过导入一个配置类, 然后配置类中的创建组件的方法生效, 就可以将定义的组件注册到IOC容器中
@Import(value = {SymTemplateAutoConfiguration.class})
public @interface EnableSymTemplate {
}
