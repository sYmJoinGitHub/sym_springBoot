package com.sym.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异步任务service
 * <p>
 * Created by 沈燕明 on 2018/11/28.
 */
public class AsyncService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);

    /**
     * 只有调用调用此方法才有异步效果
     */
    @Async
    public void sendMail() {
        LOGGER.info(Thread.currentThread().getName());
    }


    /**
     * 如果本方法调用另一个被{@link Async}修饰的方法，那个方法是不会用异步效果
     */
    @RequestMapping("run")
    public void commonRun() {
        LOGGER.info(Thread.currentThread().getName());
        this.sendMail();
    }
}
