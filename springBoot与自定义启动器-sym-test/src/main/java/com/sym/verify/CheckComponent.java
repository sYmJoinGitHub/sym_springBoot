package com.sym.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 根据不同环境，判断是否用注入我们自定义的组件
 *
 * Created by 沈燕明 on 2019/5/30 17:04.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckComponent {

    @Autowired
    private ApplicationContext context;
    // Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

    /**
     * 什么都不变，判断是否会生效
     */
    @Test
    public void test(){
        this.checkExist();
    }

    /**
     * 去掉web环境，即删除web-starter，判断自定义组件是否会被注入
     */
    @Test
    public void testOne(){
        this.checkExist();
    }

    /**
     * 去掉配置属性，spring.sym.component.enable,或者将其改为false，判断自定义组件是否会被注入
     */
    @Test
    public void testTwo(){
        this.checkExist();
    }



    /**
     * 校验自定义的组件是否要注入到IOC容器中
     */
    private void checkExist(){
        if( context.containsBean("symComponent") ){
            System.out.println("自定义组件symComponent已经注入到IOC容器中");
            System.out.println("值为："+context.getBean("symComponent"));
        }else{
            System.out.println("自定义组件symComponent没有注入到IOC容器中");
        }
    }
}
