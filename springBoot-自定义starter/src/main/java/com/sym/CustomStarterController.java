package com.sym;
import com.sym.autoConfigurer.SymCompoent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义启动器starter的测试
 *
 * Created by 沈燕明 on 2018/11/11.
 */
@RestController
public class CustomStarterController {

    /**
     * 该组件是由自定义启动类starter自动装配的组件
     */
    @Autowired
    private SymCompoent symCompoent;

    @RequestMapping("hello")
    public String hello(){
        return symCompoent.helloWorld("成功了~");
    }
}
