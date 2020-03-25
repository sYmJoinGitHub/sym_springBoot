package com.sym.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author shenym
 * @date 2020/3/25 17:24
 */
@Service
public class TemplateService {

    @Autowired
    private MongoTemplate template;

    public void pong(){
        System.out.println(template);
    }
}
