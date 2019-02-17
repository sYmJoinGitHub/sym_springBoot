package com.sym.datasource.c3p0;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * Created by 沈燕明 on 2018/11/10.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class C3p0Test {

    @Autowired
    private DataSource dataSource;

    @Test
    public void dataSourceTest(){
        System.out.println( dataSource.getClass() );
    }
}
