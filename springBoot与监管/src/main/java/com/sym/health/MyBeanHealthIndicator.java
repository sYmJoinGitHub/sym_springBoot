package com.sym.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by 沈燕明 on 2018/12/4.
 */
@Component // 需要注入到IOC容器中
public class MyBeanHealthIndicator implements HealthIndicator {
    /**
     * health()方法执行判断逻辑，例子：如果是连接池的健康信息，就在这里
     * 判断连接是否生效，池中是否有指定配置的连接数...等等
     */
    @Override
    public Health health() {
        // 如果组件是健康的，返回 Health.up().build()
        // return Health.up().build();

        // 如果组件是异常的，返回 Health.down().build()，可以使用withDetail()
        // 方法返回异常的信息
        return Health.down().withDetail("msg","服务异常").build();
    }
}
