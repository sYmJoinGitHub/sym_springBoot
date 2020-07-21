package com.sym.hystrix.constants;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * Hystrix的配置参数, 官方文档：https://github.com/Netflix/Hystrix/wiki/Configuration.
 * 通过{@link HystrixCommand#commandProperties()}、{@link HystrixCommand#threadPoolProperties()}指定
 *
 * @author shenyanming
 * Created on 2020/7/21 15:35
 */
public class HystrixCommandPropertiesConstants {

    /**
     * 熔断器配置 - Circuit Breaker
     */
    public static class CircuitBreaker {
        /**
         * 是否启用熔断器, 默认为true.
         * {@link HystrixCommandProperties.Setter#withCircuitBreakerEnabled}
         */
        public static final String CIRCUIT_BREAKER_ENABLED = "circuitBreaker.enabled";

        /**
         * 是否强制启用/关闭熔断器, 默认值都为false.
         * forceOpen为true, 无论什么情况, 断路器将拒绝所有请求, 此属性优先于circuitBreaker.forceClosed.
         * forceClosed为true, 无论错误百分比如何，断路器都将允许请求.
         * {@link HystrixCommandProperties.Setter#withCircuitBreakerForceOpen}
         * {@link HystrixCommandProperties.Setter#withCircuitBreakerForceClosed}
         */
        public static final String CIRCUIT_BREAKER_FORCE_OPEN = "circuitBreaker.forceOpen";
        public static final String CIRCUIT_BREAKER_FORCE_CLOSED = "circuitBreaker.forceClosed";

        /**
         * 在一个滑动窗口期内, 如果失败的请求的数量达到此值, 则会开启熔断器, 默认值为20.
         * 例如：若设置为20，则如果在滚动窗口（例如10秒的窗口）中仅收到19个请求，即使所有19个失败，电路也不会跳闸断开.
         * 此配置项的值需要根据接口的 QPS 进行计算，建议设置为 QPS * 窗口秒数 * 60%.
         * {@link HystrixCommandProperties.Setter#withCircuitBreakerRequestVolumeThreshold}
         */
        public static final String CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD = "circuitBreaker.requestVolumeThreshold";

        /**
         * 通过滑动窗口获取到当前时间段内 Hystrix 方法执行的失败率后，根据此配置来判断是否要开启熔断器.
         * 此配置项默认值为50，即窗口时间内超过 50% 的请求失败后, 会打开熔断器将后续请求快速失败.
         * {@link HystrixCommandProperties.Setter#withCircuitBreakerErrorThresholdPercentage}
         */
        public static final String CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE = "circuitBreaker.errorThresholdPercentage";

        /**
         * 熔断器打开后，所有的请求都会快速失败，但何时服务恢复正常就是下一个要面对的问题.
         * 熔断器打开时，Hystrix 会在经过一段时间后就放行一条请求，如果这条请求执行成功了，说明此时服务很可能已经恢复了正常，
         * 那么会将熔断器关闭，如果此请求执行失败，则认为服务依然不可用，熔断器继续保持打开状态.
         * 此配置项指定了熔断器开启后经过多长时间允许一次请求尝试执行，默认值是 5000.
         * {@link HystrixCommandProperties.Setter#withCircuitBreakerSleepWindowInMilliseconds}
         */
        public static final String CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS = "circuitBreaker.sleepWindowInMilliseconds";
    }

    /**
     * 统计器配置 - Metrics.
     * <p>
     *      滑动窗口：Hystrix 的统计器是由滑动窗口来实现的，可以这么来理解滑动窗口：
     *      一位乘客坐在正在行驶的列车的靠窗座位上，列车行驶的公路两侧种着一排挺拔的白杨树，随着列车的前进，路边的白杨树迅速从窗口滑过，
     *      我们用每棵树来代表一个请求，用列车的行驶代表时间的流逝. 那么，列车上的这个窗口就是一个典型的滑动窗口，这个乘客能通过窗口看到的
     *      白杨树就是Hystrix 要统计的数据.
     * </p>
     * <p>
     *      桶：bucket 是 Hystrix 统计滑动窗口数据时的最小单位. 同样类比列车窗口，在列车速度非常快时，如果每掠过一棵树就统计一次窗口内树的数据，
     *      显然开销非常大，如果乘客将窗口分成十分，列车前进行时每掠过窗口的十分之一就统计一次数据，开销就完全可以接受了.
     *      Hystrix 的 bucket 就是窗口 N分之一 的概念.
     * </p>
     */
    public static class Metrics {
        /**
         * 指定了窗口的大小，单位是 ms，默认值是 1000，即一个滑动窗口默认统计的是 1s 内的请求数据.
         * {@link HystrixCommandProperties.Setter#withMetricsRollingStatisticalWindowInMilliseconds}
         */
        public static final String METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS = "metrics.rollingStats.timeInMilliseconds";

        /**
         * 指定了健康数据统计器（影响 Hystrix 熔断）中每个桶的大小，默认是 500m.
         * 在进行统计时，Hystrix 通过 metrics.rollingStats.timeInMilliseconds / metrics.healthSnapshot.intervalInMilliseconds 计算出桶数，
         * 在窗口滑动时，每滑过一个桶的时间间隔时就统计一次当前窗口内请求的失败率.
         * {@link HystrixCommandProperties.Setter#withMetricsHealthSnapshotIntervalInMilliseconds}
         */
        public static final String METRICS_HEALTH_SNAPSHOT_INTERVAL_IN_MILLISECONDS = "metrics.healthSnapshot.intervalInMilliseconds";

        /**
         * Hystrix 会将命令执行的结果类型都统计汇总到一块，给上层应用使用或生成统计图表，此配置指定：生成统计数据流时滑动窗口应该拆分的桶数, 默认值为10.
         * 必须保证：metrics.rollingStats.timeInMilliseconds % metrics.rollingStats.numBuckets == 0, 否则会抛出异常.
         * {@link HystrixCommandProperties.Setter#withMetricsRollingStatisticalWindowBuckets}
         */
        public static final String METRICS_ROLLING_STATS_NUM_BUCKETS = "metrics.rollingStats.numBuckets";

        /**
         * 是否统计方法响应时间百分比，默认为 true 时，Hystrix 会统计方法执行的 1%,10%,50%,90%,99% 等比例请求的平均耗时用以生成统计图表.
         * 如果禁用它，则所有摘要统计信息（平均值，百分位数）都将返回-1.
         * {@link HystrixCommandProperties.Setter#withMetricsRollingPercentileEnabled}
         */
        public static final String METRICS_ROLLING_PERCENTILE_ENABLED = "metrics.rollingPercentile.enabled";

        /**
         * 统计响应时间百分比时的窗口大小，默认为 60000，即一分钟.
         * {@link HystrixCommandProperties.Setter#withMetricsRollingPercentileWindowInMilliseconds}
         */
        public static final String METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS = "metrics.rollingPercentile.timeInMilliseconds";

        /**
         * 统计响应时间百分比时滑动窗口要划分的桶，默认为6.
         * 必须保证：metrics.rollingPercentile.timeInMilliseconds % metrics.rollingPercentile.numBuckets == 0, 否则会抛出异常.
         * {@link HystrixCommandProperties.Setter#withMetricsRollingPercentileWindowBuckets}
         */
        public static final String METRICS_ROLLING_PERCENTILE_NUM_BUCKETS = "metrics.rollingPercentile.numBuckets";

        /**
         * 统计响应时间百分比时，每个滑动窗口的桶内要保留的请求数，桶内的请求超出这个值后，会覆盖最前面保存的数据。默认值为 100.
         * 例如，如果存储桶大小设置为100，并且表示10秒的存储桶窗口，但是在此期间发生了500次执行，则在该10秒存储桶中将仅保留最后的100次执行.
         * {@link HystrixCommandProperties.Setter#withMetricsRollingPercentileBucketSize}
         */
        public static final String METRICS_ROLLING_PERCENTILE_BUCKET_SIZE = "metrics.rollingPercentile.bucketSize";
    }

    /**
     * 线程隔离配置, Isolation
     */
    public static class ThreadIsolation{
        /**
         * 配置请求隔离的方式, 有2种：线程池隔离(默认)、信号量隔离.
         * 可配置的值为：THREAD、SEMAPHORE.
         * {@link HystrixCommandProperties.Setter#withExecutionIsolationStrategy}
         */
        public final static String EXECUTION_ISOLATION_STRATEGY = "execution.isolation.strategy";

        /**
         * 是否设置超时判断, 默认为true.
         * {@link HystrixCommandProperties.Setter#withExecutionTimeoutEnabled}
         */
        public final static String EXECUTION_TIMEOUT_ENABLED = "execution.timeout.enabled";

        /**
         * 方法执行超时的时间, 单位为毫秒, 默认值为1000, 即1秒超时.
         * {@link HystrixCommandProperties.Setter#withExecutionTimeoutInMilliseconds}
         */
        public static final String EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS = "execution.isolation.thread.timeoutInMilliseconds";

        /**
         * 是否在方法执行超时/被取消时中断方法, 默认值为true.
         * 在java中不提倡强制中断一个线程, 因此这两个配置需要原方法支持响应中断信号, 否则这两个配置会被忽略.
         * {@link HystrixCommandProperties.Setter#withExecutionIsolationThreadInterruptOnTimeout}
         * {@link HystrixCommandProperties.Setter#withExecutionIsolationThreadInterruptOnFutureCancel}
         */
        public static final String EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIME_OUT = "execution.isolation.thread.interruptOnTimeout";
        public static final String EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_FUTURE_CANCEL = "execution.isolation.thread.interruptOnFutureCancel";

        /**
         * 仅当配置隔离策略为信号量, 即SEMAPHORE时, 此配置才会生效, 默认值为10.
         * 它指定了一个 Hystrix 方法使用信号量隔离时的最大并发数，超过此并发数的请求会被拒绝。信号量隔离的配置就这么一个.
         * {@link HystrixCommandProperties.Setter#withExecutionIsolationSemaphoreMaxConcurrentRequests}
         */
        public static final String EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS = "execution.isolation.semaphore.maxConcurrentRequests";
    }

    /**
     * 线程池配置
     */
    public static class ThreadPool{
        /**
         * 核心线程数. 默认为10, 一般根据 QPS * 99% cost + redundancy count 计算得出.
         * {@link HystrixThreadPoolProperties.Setter#withCoreSize}
         */
        public static final String CORE_SIZE = "coreSize";

        /**
         * 最大线程数. 默认为10, 此配置项单独配置时并不会生效，需要启用 allowMaximumSizeToDivergeFromCoreSize
         * {@link HystrixThreadPoolProperties.Setter#withMaximumSize}
         */
        public static final String MAXIMUM_SIZE = "maximumSize";

        /**
         * 是否允许线程池扩展到最大线程池数量(允许maximumSize的配置生效)，默认为 false.
         * 该值可以等于或大于coreSize, 设置coreSize < maximumSize将创建一个线程池，该线程池可以维持maximumSize并发性，
         * 但是在相对不活动期间会将线程返回到系统, 取决于keepAliveTimeInMinutes.
         * {@link HystrixThreadPoolProperties.Setter#withAllowMaximumSizeToDivergeFromCoreSize}
         */
        public static final String ALLOW_MAXIMUM_SIZE_TO_DIVERGE_FROM_CORE_SIZE = "allowMaximumSizeToDivergeFromCoreSize";

        /**
         * 线程池阻塞队列的最大值，默认值为 -1.
         * 若设置为-1时，会使用 SynchronousQueue，此时其 size 为0，Hystrix 不会向队列内存放作业;
         * 若设置为一个正的 int 型，队列会使用一个固定 size 的 LinkedBlockingQueue，在核心线程池内的线程都在忙碌时，
         * 会将作业暂时存放在此队列内，但超出此队列的请求依然会被拒绝.
         * {@link HystrixThreadPoolProperties.Setter#withMaxQueueSize}
         */
        public static final String MAX_QUEUE_SIZE = "maxQueueSize";

        /**
         * 由于 maxQueueSize 值在线程池被创建后就固定了大小，如果需要动态修改队列长度的话可以设置此值，
         * 即使队列未满，队列内作业达到此值时同样会拒绝请求, 默认值为5.
         * {@link HystrixThreadPoolProperties.Setter#withQueueSizeRejectionThreshold}
         */
        public static final String QUEUE_SIZE_REJECTION_THRESHOLD = "queueSizeRejectionThreshold";

        /**
         * 线程池非核心线程的线程存活时间, 默认值为1, 单位分钟.
         * {@link HystrixThreadPoolProperties.Setter#withKeepAliveTimeMinutes}
         */
        public static final String KEEP_ALIVE_TIME_MINUTES = "keepAliveTimeMinutes";
    }

    /**
     * 其它配置
     */
    public static class Other{
        /**
         * 是否启用请求结果缓存，默认是 true. 缓存请求结果和从缓存中获取结果都需要我们配置 cacheKey，
         * 并且在方法上使用 @CacheResult 注解声明一个缓存上下文. 需要和 HystrixCommand.getCacheKey()、HystrixRequestCache一起使用.
         * {@link HystrixCommandProperties.Setter#withRequestCacheEnabled}
         */
        public static final String REQUEST_CACHE_ENABLED = "requestCache.enabled";

        /**
         * 是否应将HystrixCommand执行和事件记录到HystrixRequestLog, 默认值true.
         * {@link HystrixCommandProperties.Setter#withRequestLogEnabled}
         */
        public static final String REQUEST_LOG_ENABLED = "requestLog.enabled";

        /**
         * 回退方法执行时的最大并发数，默认是10，如果大量请求的回退方法被执行时，超出此并发数的请求会抛出 REJECTED_SEMAPHORE_FALLBACK 异常.
         * {@link HystrixCommandProperties.Setter#withFallbackIsolationSemaphoreMaxConcurrentRequests}
         */
        public static final String FALLBACK_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS = "fallback.isolation.semaphore.maxConcurrentRequests";

        /**
         * 在发生故障或拒绝时是否允许调用HystrixCommand.getFallback(), 默认为true.
         * {@link HystrixCommandProperties.Setter#withFallbackEnabled}
         */
        public static final String FALLBACK_ENABLED = "fallback.enabled";
    }
}
