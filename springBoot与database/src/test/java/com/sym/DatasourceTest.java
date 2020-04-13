package com.sym;

import com.sym.datasource.DruidApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by 沈燕明 on 2018/11/9.
 */
@SpringBootTest(classes = {DruidApplication.class})
@ActiveProfiles("druid")
@RunWith(SpringRunner.class)
public class DatasourceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testConnection() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }


    @Test
    public void testExecute(){
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from t_temp ");
        for (Map<String, Object> map : list){
            System.out.println(map.get("id")+"\t"+map.get("testname"));
        }
    }
}
