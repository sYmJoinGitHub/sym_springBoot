package com.sym.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 沈燕明 on 2019/5/30 15:31.
 */
@RestController
public class SpringSecurityController {

    @GetMapping("get")
    public String getOne(){
        return "life is fantastic!!!";
    }

}
