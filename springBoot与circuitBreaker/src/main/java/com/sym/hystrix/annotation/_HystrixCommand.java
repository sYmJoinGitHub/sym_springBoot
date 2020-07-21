package com.sym.hystrix.annotation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;

/**
 * {@link com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand}注解的说明.
 *
 * @author shenyanming
 * Created on 2020/7/21 14:17
 */
public @interface _HystrixCommand {


    /**
     * 一组 Hystrix 命令的集合， 用来统计、报告，默认取类名，可不配置
     */
    String groupKey() default "";

    /**
     * 用来标识一个 Hystrix 命令，默认会取被注解的方法名。需要注意：Hystrix 里同一个键的唯一标识并不包括 groupKey，
     * 建议取一个独一二无的名字，防止多个方法之间因为键重复而互相影响
     */
    String commandKey() default "";

    /**
     * 用来标识一个线程池，如果没设置的话会取 groupKey，很多情况下都是同一个类内的方法在共用同一个线程池，
     * 如果两个共用同一线程池的方法上配置了同样的属性，在第一个方法被执行后线程池的属性就固定了，所以属性会以第一个被执行的方法上的配置为准.
     *
     * @return thread pool key
     */
    String threadPoolKey() default "";

    /**
     * 方法执行时熔断、错误、超时时会执行的回退方法，需要保持此方法与 Hystrix 方法的签名和返回值一致
     */
    String fallbackMethod() default "";

    /**
     * 与此命令相关的属性
     */
    HystrixProperty[] commandProperties() default {};

    /**
     * 与线程池相关的属性
     */
    HystrixProperty[] threadPoolProperties() default {};

    /**
     * 默认 Hystrix 在执行方法时捕获到异常时执行回退，并统计失败率以修改熔断器的状态，
     * 而被忽略的异常则会直接抛到外层，不会执行回退方法，也不会影响熔断器的状态.
     */
    Class<? extends Throwable>[] ignoreExceptions() default {};

    /**
     * 当 Hystrix 命令被包装成 RxJava 的 Observer 异步执行时，此配置指定了 Observable 被执行的模式，
     * 默认是 ObservableExecutionMode.EAGER，Observable 会在被创建后立刻执行，而 ObservableExecutionMode.EAGER模式下，
     * 则会产生一个 Observable 被 subscribe 后执行。我们常见的命令都是同步执行的，此配置项可以不配置.
     *
     * see {@link ObservableExecutionMode}.
     */
    ObservableExecutionMode observableExecutionMode() default ObservableExecutionMode.EAGER;

    /**
     * 当配置项包括 HystrixRuntimeException 时，所有的未被忽略的异常都会被包装成 HystrixRuntimeException，配置其他种类的异常好像并没有什么影响
     */
    HystrixException[] raiseHystrixExceptions() default {};

    /**
     * 默认回退方法，当配置 fallbackMethod 项时此项没有意义，另外，默认回退方法不能有参数，返回值要与 Hystrix方法的返回值相同
     */
    String defaultFallback() default "";

}
