package com.sym.bboss;

import com.sym.bboss.util.BbossClientUtil;
import com.sym.bboss.util.BbossUtil;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.MapRestResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Bboss无DSL脚本的方式，即与Elasticsearch官方提供的API一样，使用String拼接查询语句
 * <p>
 * Created by 沈燕明 on 2018/11/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BbossServiceByString {

    // ClientInterface 是Bboss提供的操作ES的客户端，里面封装增删改查的API接口
    private ClientInterface client;

    @Before
    public void init() {
        // 获取默认的ES客户端
        client = BbossClientUtil.getRestClient();
    }

    /**
     * 查询指定index-指定type下的所有文档信息
     */
    @Test
    public void searchAll() {
        // 组装DSL语句
        String query = "{\n" + "  \"query\": {\n" + "    \"match_all\": {}\n" + "  },\n" + "  \"size\": 20\n" + "}";
        // 查询ES，第一个参数指定index和type以及查询使用的API，第二个参数指定查询语句
        MapRestResponse response = client.search("/bank/account/_search", query);
        // 取出ES返回的数据
        Map<String, Object> map = BbossUtil.parseToMap(response, true);
        System.out.println(map);
    }

    /**
     * 新增一个索引
     */
    @Test
    public void addIndex() {
        // 组装ES创建索引的语句，是不是很恶心，还是使用DSL脚本的方式吧
        String mapping = "{\n" + "\"settings\" : {\n" + "\t \"number_of_shards\" : 1\n" + "\t },\n" + "\t \"mappings\" : {\n" + "\t  \"love\" : {\n" + "\t  \"properties\" : {\n" + "\t  " + "             \"sqx\" : { \"type\" : \"text\" }\n" + "\t  }\n" + "\t  }\n" + "\t  }\n" + "\t }";
        client.createIndiceMapping("sym", mapping);
    }

    /**
     * 删除一个索引
     */
    @Test
    public void deleteIndex() {
        String res = client.dropIndice("sym");
        //System.out.println(GsonUtil.JsonToMap(res));
    }


    /**
     * 新增一个文档
     */
    @Test
    public void addDocument() {
        // 增加一个文档
        Map<String, Object> param = new HashMap<>();
        param.put("id", 1);
        param.put("studentNumber", "3146017029");
        param.put("name", "shenym");
        param.put("age", 24);
        param.put("sex", 'm');
        // 设置ES的索引index和类型type
        String _index = "test";
        String _type = "student";
        // 执行添加操作
        String s = client.addDocumentWithId(_index, _type, param, "1");
        // 返回结果为JSON字符串
//        Map<String, Object> map = GsonUtil.JsonToMap(s);
//        System.out.println(map);
    }

    /**
     * 修改一个文档
     */
    @Test
    public void updateDocument() {
        // Bboss默认会帮我们以局部更新的方式更新文档,传入什么参数就只修改那个参数而已
        Map<String, Object> param = new HashMap<>();
        param.put("id", 2);
        param.put("studentNumber", "3146017031");
        // 设置ES的索引index和类型type
        String _index = "test";
        String _type = "student";
        // 执行修改操作
        String s = client.updateDocument(_index, _type, "1", param);
        // 结果为JSON字符串
        //Map<String, Object> map = GsonUtil.JsonToMap(s);
        //System.out.println(map);
    }


    /**
     * 删除一个文档
     */
    @Test
    public void deleteDocument() {
        // 根据_id来删除
        String s = client.deleteDocument("test", "student", "1");
        // 结果为JSON字符串
        //Map<String, Object> map = GsonUtil.JsonToMap(s);
        //System.out.println(map);
    }


}
