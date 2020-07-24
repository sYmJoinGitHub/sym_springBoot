package com.sym.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import static com.sym.sentinel.constants.SentinelResourceNameConstants.SENTINEL_PROXY_CONTROLLER_GET_ONE_V1;

/**
 * Sentinel 的所有规则都可以在内存态中动态地查询及修改，修改之后立即生效. 同时 Sentinel 也提供相关 API, 可以定制自己的规则策略.
 *
 * @author shenyanming
 * Created on 2020/7/23 16:49
 */
@Component
public class SentinelConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet()  {
        initFlowQpsRule();
        initDegradeRule();
        //initSystemRule();
        //initAuthorityRule();
        //initParamFlowRule();
    }

    /**
     * 流量控制规则, {@link FlowRule}, 重要属性如下:
     * - resource：资源名, 是限流规则的作用对象;
     * - count：限流阈值;
     * - grade：限流阈值类型, QPS 模式（1）或并发线程数模式（0）.默认QPS模式;
     * - limitApp：流控针对的调用来源. 默认default, 代表不区分调用来源;
     * - strategy：调用关系限流策略：直接、链路、关联. 默认根据资源本身（直接）
     * - controlBehavior：流控效果（直接拒绝/WarmUp/匀速+排队等待）, 不支持按调用关系限流. 默认直接拒绝
     * - clusterMode：是否集群限流. 默认否
     */
    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();

        // 资源1的规则
        FlowRule rule = new FlowRule(SENTINEL_PROXY_CONTROLLER_GET_ONE_V1);
        // 设置并发线程数为1
        rule.setCount(1);
        rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        rule.setLimitApp("default");
        // 设置调控效果：0. default(reject directly), 1. warm up, 2. rate limiter, 3. warm up + rate limiter
        rule.setControlBehavior(0);
        rules.add(rule);

        // 若干资源的规则
        // ...

        // 最终提交到FlowRuleManager中.
        FlowRuleManager.loadRules(rules);
    }


    /**
     * 熔断降级规则, {@link DegradeRule}, 重要属性如下：
     * - resource：资源名，即规则的作用对象;
     * - count：阈值;
     * - grade：熔断策略, 支持 秒级RT、秒级异常比例、分钟级异常数. 默认秒级平均 RT;
     * - timeWindow：降级的时间，单位为 s;
     * - rtSlowRequestAmount：RT 模式下 1 秒内连续多少个请求的平均 RT 超出阈值方可触发熔断. 默认5个;
     * - minRequestAmount：异常熔断的触发最小请求数, 请求数小于该值时即使异常比率超出阈值也不会熔断. 默认5个.
     */
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(SENTINEL_PROXY_CONTROLLER_GET_ONE_V1);
        // set threshold RT, 10 ms
        rule.setCount(10);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    /**
     * 系统保护规则, {@link SystemRule}, Sentinel 自适应限流从整体维度对应用入口流量进行控制, 结合应用的 Load、CPU 使用率、总体平均 RT、入口 QPS 和并发线程数等几个维度的监控指标,
     * 通过自适应的流控策略, 让系统的入口流量和系统的负载达到一个平衡, 让系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性.
     * 重要属性如下：
     * - highestSystemLoad：load1 触发值, 用于触发自适应控制阶段. 默认值为-1, 不生效.
     * - avgRt：所有入口流量的平均响应时间, 默认值为-1, 不生效.
     * - maxThread：入口流量的最大并发数, 默认值-1, 不生效.
     * - qps：所有入口资源的 QPS, 默认值-1, 不生效.
     * - highestCpuUsage：当前系统的 CPU 使用率（0.0-1.0）, 默认值-1, 不生效
     */
    private void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setResource(SENTINEL_PROXY_CONTROLLER_GET_ONE_V1);
        rule.setHighestSystemLoad(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }


    /**
     * 访问控制规则, {@link AuthorityRule}, 黑白名单根据资源的请求来源（origin）限制资源是否通过,
     * 若配置白名单则只有请求来源位于白名单内时才可通过; 若配置黑名单则请求来源位于黑名单时不通过, 其余的请求通过.
     * 重要属性为:
     * resource：资源名，即规则的作用对象
     * limitApp：对应的黑名单/白名单，不同 origin 用 , 分隔，如 appA,appB
     * strategy：限制模式，AUTHORITY_WHITE 为白名单模式，AUTHORITY_BLACK 为黑名单模式，默认为白名单模式
     */
    private void initAuthorityRule(){
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(SENTINEL_PROXY_CONTROLLER_GET_ONE_V1);
        rule.setStrategy(1);
        rule.setLimitApp("default");
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);
    }


    /**
     * 热点参数规则（ParamFlowRule）类似于流量控制规则（FlowRule）,重要参数：
     *
     * resource：资源名，必填
     * count：限流阈值，必填
     * grade：限流模式	QPS 模式
     * durationInSec：统计窗口时间长度（单位为秒），1.6.0 版本开始支持	1s
     * controlBehavior：流控效果（支持快速失败和匀速排队模式），1.6.0 版本开始支持	快速失败
     * maxQueueingTimeMs：最大排队等待时长（仅在匀速排队模式生效），1.6.0 版本开始支持	0ms
     * paramIdx：热点参数的索引，必填，对应 SphU.entry(xxx, args) 中的参数索引位置
     * paramFlowItemList：参数例外项，可以针对指定的参数值单独设置限流阈值，不受前面 count 阈值的限制。仅支持基本类型和字符串类型
     * clusterMode：是否是集群参数流控规则	false
     * clusterConfig：集群流控相关配置
     */
    private void initParamFlowRule(){
        List<ParamFlowRule> list = new ArrayList<>();
        ParamFlowRule rule = new ParamFlowRule();
        list.add(rule);
        ParamFlowRuleManager.loadRules(list);
    }
}
