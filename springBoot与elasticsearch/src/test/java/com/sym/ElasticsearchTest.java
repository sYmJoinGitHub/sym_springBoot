package com.sym;

import com.sym.jest.JestClientService;
import com.sym.rest.RestClientService;
import com.sym.template.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author shenym
 * @date 2020/3/25 16:46
 */
@SpringBootTest
public class ElasticsearchTest {

    @Autowired
    private RestClientService restClientService;

    @Autowired
    private JestClientService jestClientService;

    @Autowired
    private TemplateService templateService;

    @Test
    public void pongTest() throws IOException {
        restClientService.connectionTest();
    }

    @Test
    public void queryTest() throws IOException {
        restClientService.query();
    }

    @Test
    public void pongJestTest(){
        jestClientService.connectionTest();
    }

    @Test
    public void queryJestTest() throws IOException {
        jestClientService.query();
    }

    @Test
    public void queryTemplate(){
        templateService.query();
    }
}
