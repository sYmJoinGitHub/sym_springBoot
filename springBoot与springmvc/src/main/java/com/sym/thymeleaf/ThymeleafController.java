package com.sym.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 沈燕明 on 2018/10/5.
 */
@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

    /**
     * springBoot对thymeleaf的自动配置，页面跳转：前缀为"classpath:/templates"，后缀为".html"
     * @return
     * @throws Exception
     */
    @RequestMapping("hello")
    public String forWardPage(Model model) throws Exception{
        // 传递字符串
        model.addAttribute("msg","hello!thymeleaf");
        // 传递对象
        ThymeleafBean bean = new ThymeleafBean(110,"警察");
        model.addAttribute("bean",bean);
        // 传递map
        Map<String,Object> map = new HashMap<>();
        map.put("name","沈燕明");
        model.addAttribute("map",map);
        // 传递list
        List<String> list = new ArrayList<>();
        list.add("疾风剑豪");
        list.add("无极剑圣");
        list.add("无双剑姬");
        list.add("暗裔剑魔");
        model.addAttribute("list",list);

        return "thymeleaf-study";
    }
}
