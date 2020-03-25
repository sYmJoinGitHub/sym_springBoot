package com.sym.map.service;
import com.sym.map.domain.CacheBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * 当缓存不存在时或者需要重新缓存时，就会调用数据库读取数据
 * 这里模拟查询数据库
 *
 * Created by 沈燕明 on 2018/11/15.
 */
@Component
public class CacheMapper {

    private static Map<Integer, CacheBean> dataMap;

    static{
        dataMap = new HashMap<>();
        dataMap.put(110,new CacheBean(1,"张三"));
        dataMap.put(119,new CacheBean(2,"李四"));
        dataMap.put(120,new CacheBean(3,"王五"));
        dataMap.put(114,new CacheBean(4,"赵六"));
    }

    /**
     * 查询
     */
    public CacheBean selectOne(Integer id){
        return dataMap.get(id);
    }

    /**
     * 更新
     */
    public void update(CacheBean bean){
        dataMap.put(bean.getId(),bean);
    }

    /**
     * 删除
     */
    public void  delete(Integer id){
        dataMap.remove(id);
    }
}
