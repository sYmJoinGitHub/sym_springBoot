package com.sym.servlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springBoot知识点-内嵌式Servlet配置的Controller
 *
 * Created by 沈燕明 on 2018/10/31.
 */
@RestController
public class ServletController {

    /**
     * 测试自定义的Servlet
     * 如果自定义的Servlet和Controller里面的url映射地址一样，只会执行Servlet
     */
    @RequestMapping("/servlet")
    public String servletRequest(){
        return "my servlet process...";
    }

    /**
     * 测试自定义的Filter
     */
    @RequestMapping("/filter")
    public String filterRequest(){
        return "my filter process...";
    }

}
