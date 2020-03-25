package com.sym.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author shenym
 * @date 2020/3/25 17:05
 */
@Service
public class TemplateService {

    private final ElasticsearchTemplate esTemplate;

    public TemplateService(ElasticsearchTemplate elasticsearchTemplate) {
        this.esTemplate = elasticsearchTemplate;
    }

    public void query(){
        boolean b = esTemplate.typeExists("test", "test");
        System.out.println(b);
    }
}
