package com.sym.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author shenyanming
 * Created on 2020/7/8 14:27
 */
@Slf4j
@Component
public class ApolloConfigListener {

    /**
     * 通过注解也可以创建监听器, 默认监听的namespace是application
     * @param changeEvent 监听事件
     */
    @ApolloConfigChangeListener({"application","db.config"})
    private void someOnChange(ConfigChangeEvent changeEvent) {
        log.info("监听配置, namespace: {}", changeEvent.getNamespace());

        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            log.info("监听配置,  key:{}, oldValue:{}, newValue:{}, changeType:{}",
                    change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType());
        }
    }
}
