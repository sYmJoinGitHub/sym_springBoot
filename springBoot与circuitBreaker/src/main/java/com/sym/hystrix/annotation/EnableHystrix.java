package com.sym.hystrix.annotation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 引入{@link HystrixCommandAspect}以便开启Hystrix注解功能,
 * 拦截{@link HystrixCommand}命令
 *
 * @author shenyanming
 * Created on 2020/7/21 17:29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(HystrixCommandAspect.class)
public @interface EnableHystrix {
}
