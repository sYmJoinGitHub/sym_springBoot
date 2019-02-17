package com.sym.datasource.jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 如果没有配置任何数据源，springBoot默认使用Tomcat自带的数据池中的数据源
 *
 * Created by 沈燕明 on 2018/11/9.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JDBCTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试数据源
     * @throws SQLException
     */
    @Test
    public void testDataSource() throws SQLException{
        System.out.println("springBoot使用的数据源："+dataSource.getClass());
        System.out.println("获取的连接："+dataSource.getConnection());
    }

    /**
     * 测试原生的JDBC
     */
    @Test
    public void testJdbc(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_temp ");
        for (Map map : list){
            System.out.println(map.get("id")+"\t"+map.get("testname"));
        }
    }

}
