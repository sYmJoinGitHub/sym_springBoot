package com.sym.rest;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author shenym
 * @date 2020/3/25 16:43
 */
@Service
public class RestClientService {

    /**
     * elasticsearch驱动提供的低级客户端
     */
    private final RestClient restClient;

    /**
     * elasticsearch驱动提供的高级客户端
     */
    private final RestHighLevelClient restHighLevelClient;

    public RestClientService(@Qualifier("elasticsearchRestClient") RestClient restClient,
                             RestHighLevelClient restHighLevelClient) {
        this.restClient = restClient;
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 测试连接
     */
    public void connectionTest() throws IOException {
        System.out.println(restClient.getNodes());
        boolean ping = restHighLevelClient.ping(RequestOptions.DEFAULT);
        System.out.println(ping);
    }

    /**
     * 查询
     */
    public void query() throws IOException {
        GetResponse response = restHighLevelClient.get(new GetRequest(), RequestOptions.DEFAULT);
        Map<String, Object> source = response.getSource();
        System.out.println(source);
    }
}
