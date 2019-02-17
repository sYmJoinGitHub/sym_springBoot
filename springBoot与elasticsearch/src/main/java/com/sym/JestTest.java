package com.sym;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by 沈燕明 on 2018/11/26.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JestTest {

    @Autowired
    private JestClient jestClient;

    @Test
    public void testOne(){
        String sql = "{\"query\":{\"match_all\":{}}}";
        Search search = new Search.Builder(sql).addIndex("bank").addType("account").build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            String jsonString = searchResult.getJsonString();
            System.out.println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
