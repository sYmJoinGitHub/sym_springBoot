package com.sym.controller;

import com.sym.bean.SymConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenyanming
 * Created on 2020/7/8 14:51
 */
@RestController
public class ConfigBeanController {

    @Autowired
    private SymConfigBean symConfigBean;

    @GetMapping
    public SymConfigBean info(){
        return this.symConfigBean;
    }
}
