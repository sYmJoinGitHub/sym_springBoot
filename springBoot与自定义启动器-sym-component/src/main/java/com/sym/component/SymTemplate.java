package com.sym.component;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的组件, 必须使用{@link com.sym.annotation.EnableSymTemplate}注解
 * 才会注入到 springBoot ioc 中, 才能使用
 *
 * @author ym.shen
 * Created on 2020/4/11 10:56
 */
@Slf4j
public class SymTemplate {

    /**
     * 模拟方法
     */
    public void doRun(){
        // do something
        log.info("自定义组件生效~");
    }
}
