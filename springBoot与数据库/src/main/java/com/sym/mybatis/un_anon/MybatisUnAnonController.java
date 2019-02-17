package com.sym.mybatis.un_anon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springBoot与mybatis整合-配置文件版
 * Created by 沈燕明 on 2018/11/11.
 */
@RestController()
@RequestMapping("unAnon")
public class MybatisUnAnonController {

    @Autowired
    private MybatisUnAnonMapper mapper;

    @RequestMapping("add")
    public MybatisUnAnonBean add(MybatisUnAnonBean bean){
        mapper.add( bean );
        return bean;
    }

    @RequestMapping("select/{id}")
    public MybatisUnAnonBean select(@PathVariable("id") String id){
        return mapper.select(id);
    }
}
