package com.sym.controller;

import com.sym.vo.SimpleInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenyanming
 * @date 2020/8/3 21:48.
 */
@Slf4j
@Controller
//跨域处理
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class SimpleController {

    /**
     * springBoot对thymeleaf的自动配置,
     * 页面跳转：前缀为"classpath:/templates"，后缀为".html"
     */
    @RequestMapping("/thymeleaf")
    public String forWardPage(Model model) throws Exception {
        // 传递字符串
        model.addAttribute("msg", "hello!thymeleaf");
        // 传递对象
        SimpleInfoVO vo = new SimpleInfoVO(110, "警察");
        model.addAttribute("bean", vo);
        // 传递map
        Map<String, Object> map = new HashMap<>();
        map.put("name", "沈燕明");
        model.addAttribute("map", map);
        // 传递list
        List<String> list = new ArrayList<>();
        list.add("疾风剑豪");
        list.add("无极剑圣");
        list.add("无双剑姬");
        list.add("暗裔剑魔");
        model.addAttribute("list", list);

        return "thymeleaf-study";
    }

    /**
     * springBoot的接收put请求等Restful风格API
     */
    @PutMapping("/put")
    @ResponseBody
    public String put() {
        return "这是一个put请求";
    }


    /**
     * {@link RequestMapping}特殊用法：
     * 寻找配置 `${first_mapping}`作为映射的url, 若未找到, 则用`${sencond_mapping}`,
     * 如果都未找到, 则使用默认值`/test`.
     */
    @RequestMapping("${first_mapping:${sencond_mapping:/test}}")
    @ResponseBody
    public String test(HttpServletRequest request) {
        log.info(request.getSession().getId());
        return "测试~~~";
    }


    /**
     * 模拟出现服务器内部错误导致的异常
     */
    @RequestMapping("/exception")
    public void buildException() {
        throw new UnsupportedOperationException();
    }

    /**
     * 模拟出现400错误
     */
    @RequestMapping("/404")
    @ResponseBody
    public String buildException(@RequestParam String param) {
        log.info("param: {}", param);
        return "";
    }

    /**
     * 如果自定义的Servlet和Controller里面的url映射地址一样，只会执行Servlet
     */
    @RequestMapping("/servlet")
    @ResponseBody
    public String servletRequest() {
        return "my servlet process...";
    }

    /**
     * 测试自定义的Filter
     */
    @RequestMapping("/filter")
    @ResponseBody
    public String filterRequest() {
        return "my filter process...";
    }

}
