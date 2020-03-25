package com.sym;

import com.sym.mybatis.annotation.AnnotationMapper;
import com.sym.mybatis.domain.SimpleEntity;
import com.sym.mybatis.xml.XmlMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author shenym
 * @date 2020/3/25 10:04
 */
@SuppressWarnings("ALL")
@SpringBootTest
public class MybatisTest {

    @Autowired
    private AnnotationMapper annotationMapper;

    @Autowired
    private XmlMapper xmlMapper;

    @Test
    public void addTest() {
        SimpleEntity simpleEntity = new SimpleEntity(1, "test");
        annotationMapper.add(simpleEntity);
    }

    @Test
    public void deleteTest() {
        annotationMapper.delete(1);
    }

    @Test
    public void selectTest() {
        SimpleEntity simpleEntity = annotationMapper.selectOne(1);
        System.out.println(simpleEntity);
    }

    @Test
    public void xmlAddTest() {
        xmlMapper.add(new SimpleEntity(2, "test"));
    }

    @Test
    public void xmlSelectTest() {
        SimpleEntity entity = xmlMapper.select(2);
        System.out.println(entity);
    }
}
