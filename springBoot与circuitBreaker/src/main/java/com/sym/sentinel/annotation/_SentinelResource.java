package com.sym.sentinel.annotation;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;

/**
 * {@link SentinelResource}注解属性的诠释.
 * 若 blockHandler 和 fallback 都进行了配置, 则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑.
 * 若未配置 blockHandler、fallback 和 defaultFallback, 则被限流降级时会将 BlockException 直接抛出
 * （若方法本身未定义 throws BlockException 则会被 JVM 包装一层 UndeclaredThrowableException）
 *
 * @author shenyanming
 * Created on 2020/7/23 14:14
 */
public @interface _SentinelResource {

    /**
     * 资源名称, 不能为空.
     */
    String value() default "";


    /**
     * 表示此资源是入站流量, 还是出站流量, 默认是出站.
     */
    EntryType entryType() default EntryType.OUT;


    /**
     * 设置资源的类型
     */
    int resourceType() default 0;


    /**
     * blockHandler 对应处理 BlockException 的函数名称, 要求此函数访问范围需要是 public, 返回类型需要与原方法相匹配,
     * 参数类型需要和原方法相匹配并且最后加一个额外的参数, 类型为 BlockException.
     * blockHandler 函数默认需要和原方法在同一个类中, 若希望使用其他类的函数, 则可以指定 blockHandlerClass 为对应的类的 Class 对象,
     * 注意对应的函数必需为 static 函数，否则无法解析.
     */
    String blockHandler() default "";
    Class<?>[] blockHandlerClass() default {};


    /**
     * fallback 函数名称, 用于在抛出异常的时候提供 fallback 处理逻辑, fallback 函数可以针对所有类型的异常
     * （除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理. fallback 函数签名和位置要求：
     * - 返回值类型必须与原函数返回值类型一致；
     * - 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常;
     * - fallback 函数默认需要和原方法在同一个类中, 若希望使用其他类的函数, 则可以指定 fallbackClass 为对应的类的 Class 对象,
     * 注意对应的函数必需为 static 函数, 否则无法解析.
     */
    String fallback() default "";
    Class<?>[] fallbackClass() default {};


    /**
     * 默认的 fallback 函数名称, 通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）, 默认 fallback 函数可以针对所有类型的异常
     * （除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理. 若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效.
     * defaultFallback 函数签名要求：
     * - 返回值类型必须与原函数返回值类型一致；
     * - 方法参数列表需要为空，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常;
     * - defaultFallback 函数默认需要和原方法在同一个类中, 若希望使用其他类的函数, 则可以指定 fallbackClass 为对应的类的 Class 对象，
     * 注意对应的函数必需为 static 函数, 否则无法解析.
     */
    String defaultFallback() default "";


    /**
     * 设置要跟踪的异常列表
     */
    Class<? extends Throwable>[] exceptionsToTrace() default {Throwable.class};


    /**
     * 用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出
     */
    Class<? extends Throwable>[] exceptionsToIgnore() default {};
}
