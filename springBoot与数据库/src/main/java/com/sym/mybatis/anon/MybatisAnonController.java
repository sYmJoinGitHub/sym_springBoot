package com.sym.mybatis.anon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springBoot与mybatis整合-注解版
 * Created by 沈燕明 on 2018/11/10.
 */
@RestController()
@RequestMapping("anon")
public class MybatisAnonController {

    /* 为了方便，这边就省略了service层和dao层 */
    @Autowired
    private MybatisAnonMapper mybatisAnonMapper;

    @RequestMapping("add")
    public MybatisAnonBean add(MybatisAnonBean bean){
        mybatisAnonMapper.add( bean );
        return bean;
    }

    @RequestMapping("delete/{id}")
    public int delete(@PathVariable("id") String id){
        return mybatisAnonMapper.delete(id);
    }

    @RequestMapping("select/{id}")
    public MybatisAnonBean select(@PathVariable("id") String id){
        return mybatisAnonMapper.selectOne(id);
    }
}
