package com.sym.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * springBoot执行定时任务，在启动类或者配置类上添加 @Scheduling注解
 * 在需要定时执行的方法上添加 @Scheduled注解，主要是对cron表达式的学习
 * <p>
 * Created by 沈燕明 on 2018/11/28.
 */
@Service
public class JobService {

    @Scheduled(cron = "* * * * * *")
    public void one() {
        System.out.println("每时每刻都在执行");
    }

    @Scheduled(cron = "0 * * * * 1-6")
    public void two(){
        System.out.println("每个星期一到星期六的0秒，即整分钟执行");
    }

    @Scheduled(cron = "10 * 22,23 * * *")
    public void three(){
        System.out.println("每天的22点和23点的每个分钟内的第10秒执行");
    }

//    @Scheduled(cron = "0/10 * * ? 10-12 3L")
//    public void four(){
//        System.out.println("每年的10月到12月的最后一个星期3的每个小时每个分钟内的0秒开始，每隔10秒执行");
//    }
//
//    @Scheduled(cron = "0 0 2 LW * ?")
//    public void five(){
//        System.out.println("每月的最后一个工作日凌晨2点整执行");
//    }
//
//    @Scheduled(cron = "0 0 2-4 ? * 1#1")
//    public void six(){
//        System.out.println("每月的第一个星期一的凌晨2点-4点，每个整点");
//    }
}
