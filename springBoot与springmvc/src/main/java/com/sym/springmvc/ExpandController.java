package com.sym.springmvc;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 扩展springMVC知识点使用的controller
 *
 * Created by 沈燕明 on 2018/10/25.
 */
@Controller
public class ExpandController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExpandController.class);

    /**
     * springBoot的接收put请求等RESTful风格API
     * @return
     */
    @PutMapping("put")
    @ResponseBody
    public String putRequest(){
        return "这是一个put请求";
    }


    /**
     * @RequestMapping 的特殊用法，意思是：
     * 从配置文件中去testMapping1的值作为映射的url，
     * 如果没有testMapping1，则取testMapping2的值作为映射的url，
     * 如果连testMapping2也没有，则以/test映射
     *
     * 语法为：${arg1:arg2}，取arg1的值，若arg1不存在，则取arg2的值
     *
     * @return
     */
    @RequestMapping("${testMapping1:${testMapping2:/test}}")
    @ResponseBody
    public String test(HttpServletRequest request){
        LOGGER.info(request.getSession().getId());
        return "666666";
    }


    /**
     * 模拟出现服务器内部错误导致的异常
     * @return
     */
    @RequestMapping("excepOne")
    @ResponseBody
    public String exceptionOne(){
        int i = 1/0;
        return "";
    }
    /**
     * 模拟出现400错误
     * @return
     */
    @RequestMapping("excepThree")
    @ResponseBody
    public String exceptionThree(@RequestParam String param){
        System.out.println(param);
        return "";
    }

}
