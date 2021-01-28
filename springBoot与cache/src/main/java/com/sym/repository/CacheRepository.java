package com.sym.repository;

import com.sym.domain.CacheBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 当缓存不存在时或者需要重新缓存时，就会调用数据库读取数据
 * 这里模拟查询数据库
 * <p>
 * Created by 沈燕明 on 2018/11/15.
 */
@Slf4j
@Component
public class CacheRepository {

    private static Map<Integer, CacheBean> dataMap;

    static {
        dataMap = new HashMap<>();
        dataMap.put(110, new CacheBean(1, "张三"));
        dataMap.put(119, new CacheBean(2, "李四"));
        dataMap.put(120, new CacheBean(3, "王五"));
        dataMap.put(114, new CacheBean(4, "赵六"));
    }

    /**
     * 查询
     */
    public CacheBean selectOne(Integer id) {
        log.info("数据库查询, id={}", id);
        return dataMap.get(id);
    }

    /**
     * 更新
     */
    public void update(CacheBean bean) {
        log.info("数据库更新, bean={}", bean);
        dataMap.put(bean.getId(), bean);
    }

    /**
     * 删除
     */
    public void delete(Integer id) {
        log.info("数据库删除, id={}", id);
        dataMap.remove(id);
    }
}
