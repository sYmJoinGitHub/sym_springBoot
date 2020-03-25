package com.sym.jest;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.settings.GetSettings;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author shenym
 * @date 2020/3/25 16:58
 */
@Service
public class JestClientService {

    private final JestClient jestClient;

    public JestClientService(JestClient jestClient) {
        this.jestClient = jestClient;
    }

    public void connectionTest(){
        System.out.println(jestClient);
    }

    public void query() throws IOException {
        GetSettings getSettings = new GetSettings.Builder().addIndex("test").build();
        JestResult jestResult = jestClient.execute(getSettings);
        String jsonString = jestResult.getJsonString();
        System.out.println(jsonString);
    }
}
