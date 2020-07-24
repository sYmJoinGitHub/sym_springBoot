package com.sym.sentinel.annotation;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 引入{@link SentinelResourceAspect}开启Sentinel的注解功能
 *
 * @author shenyanming
 * Created on 2020/7/23 14:13
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SentinelResourceAspect.class)
public @interface EnableSentinel {
}
