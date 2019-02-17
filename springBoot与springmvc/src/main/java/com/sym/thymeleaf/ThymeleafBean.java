package com.sym.thymeleaf;

/**
 * Created by 沈燕明 on 2018/10/5.
 */
public class ThymeleafBean {

    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ThymeleafBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
