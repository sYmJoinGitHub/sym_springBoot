package com.sym;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 沈燕明 on 2018/10/19.
 */
@Controller
public class I18nController {

    @RequestMapping("/")
    public String pageToI18n() throws Exception{
        return "i18n-study.html";
    }

    @RequestMapping("/change")
    public String changeToI18n(String l) throws Exception{
        return "i18n-study.html";
    }
}
