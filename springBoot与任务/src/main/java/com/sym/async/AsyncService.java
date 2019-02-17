package com.sym.async;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by 沈燕明 on 2018/11/28.
 */
@Service
public class AsyncService {

    /**
     * 使用异步任务来发送邮件
     */
    @Async
    public void sendMail(){

    }
}
