package com.sym.bboss;


import com.sym.bboss.bean.AccountBean;
import com.sym.bboss.util.BbossClientUtil;
import com.sym.bboss.util.BbossUtil;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.MapRestResponse;
import org.frameworkset.elasticsearch.entity.RestResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重头戏，这才是Bboss真正的用法，使用类似Mybatis的mapper映射SQL的方式来映射ES的DSL查询语言
 * <p>
 * Created by 沈燕明 on 2018/11/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BbossServiceByDSL {

    // getConfigRestClientUtil方法获取的ClientInterface实例是getRestClientUtil方法获取到的ClientInterface实例的子类
    // 所以同样具备后者的所有功能，意味着加载配置文件api和不加载配置文件api都是一致的
    // 区别就是加载配置文件api传递的是dsl在配置文件中dsl对应的名称
    private ClientInterface client;

    @Before
    public void init() {
        client = BbossClientUtil.getConfigClient("es_dsl_mapper.xml");
    }

    /**
     * 以无参的方式查询所有文档
     */
    @Test
    public void selectAllWithNoTParam() {
        String path = "/bank/account/_search"; // 要查询的_index和_type以及ES的查询API：_search
        String dslName = "queryAll"; // DSL模板的名称
        RestResponse response = client.search(path, dslName, AccountBean.class);
        // 将返回结果映射成Map
        Map<String, Object> map = BbossUtil.readRestResponse(response);
        System.out.println(map);
    }

    /**
     * 带参数匹配查询: 以Map传参 or JavaBean传参
     * DSL脚本取值使用：#[] 和 $[]， 它们的区别与mybatis中的一样，#[]会判断变量类型，而${}是直接替换值
     */
    @Test
    public void selectWithParam() {
        // 查询参数需要放到Map中，xml文件可以用key来取出查询参数
        Map<String, Object> param = new HashMap<>();
        param.put("age", 22);
        // 查询
        String path = "/bank/account/_search"; // 要查询的_index和_type以及ES的查询API：_search
        String dslName = "query_match"; // DSL模板的名称
        MapRestResponse response = client.search(path, dslName, param);
        // 将返回结果映射成Map
        Map<String, Object> map = BbossUtil.parseToMap(response,false);
        System.out.println(map);
    }


    /**
     * DSL脚本中的逻辑判断语法：#if-#else-#end,#if-#elseif-#else-#end
     * 注意在使用逻辑判断时，取参数要使用$xxx语法，而不能使用#[]语法，详见esMapper.xml
     * query_withIf
     */
    @Test
    public void selectWithIf() {
        // 查询路径与查询模板名称
        String path = "/bank/account/_search";
        String dslName = "query_withIf";
        // 查询参数
        AccountBean param = new AccountBean();
        param.setAge(37);
        param.setGender("F");
        // 执行查询
        MapRestResponse response = client.search(path, dslName, param);
        // JSON映射
        Map<String, Object> map = BbossUtil.parseToMap(response,false);
        System.out.println(map);
    }


    /**
     * DSL脚本的遍历语法：#foreach-#end,循环控制计数器变量：$velocityCount
     * query_withForeach
     */
    @Test
    public void selectWithForeach() {
        // 查询的索引和查询的API
        String path = "/bank/account/_mget";
        String dslName = "query_withForeach";
        // 查询参数
        Map<String, Object> param = new HashMap<>();
        List<String> list = Arrays.asList("44", "99", "119");
        param.put("ids", list);
        // 开始查询
        MapRestResponse response = client.search(path, dslName, param);
        // JSON映射
        Map<String, Object> map = BbossUtil.parseToMap(response,false);
        System.out.println(map);
    }
}
