package com.sym.springDataJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 沈燕明 on 2018/11/11.
 */
@RestController
@RequestMapping("jpa")
public class SpringDataJPAController {

    @Autowired
    private SpringDataJPARepository springDataJPARepository;

    @RequestMapping("get/{id}")
    public UserBean get(@PathVariable("id") String id){
        return springDataJPARepository.findOne(Integer.parseInt(id));
    }

    @RequestMapping("add")
    public UserBean add(UserBean userBean){
        return springDataJPARepository.save(userBean);
    }
}
