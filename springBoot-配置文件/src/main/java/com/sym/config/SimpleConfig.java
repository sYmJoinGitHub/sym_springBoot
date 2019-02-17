package com.sym.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用 @Configuration 告诉springBoot这是一个配置类
 * 
 * @author 沈燕明
 *
 */
@Configuration
public class SimpleConfig {
	
	/**
	 * 使用 @Bean 可以将方法返回值注入到springBoot的IOC容器中
	 * 组件名默认是方法名,可以使用 @Bean 的name属性改变组件名
	 * @return
	 */
	@Bean()
	public String girl(){
		return "Help!I need a girl";
	}

}
