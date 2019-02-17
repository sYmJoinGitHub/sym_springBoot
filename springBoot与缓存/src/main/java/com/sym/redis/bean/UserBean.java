package com.sym.redis.bean;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 如果想缓存对象实体类，该对象实体类需要实现 Serializable接口
 *
 * Created by 沈燕明 on 2018/11/22.
 */
public class UserBean implements Serializable {
    @Setter @Getter
    private Integer userId;
    @Setter @Getter
    private String userName;
    @Setter @Getter
    private String userPassword;
    @Setter @Getter
    private Boolean isDel;
    @Setter @Getter
    private List<RoleBean> roleList;

    public UserBean() {
    }

    public UserBean(Integer userId, String userName, String userPassword, Boolean isDel) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.isDel = isDel;
    }

    public UserBean(Integer userId, String userName, String userPassword, Boolean isDel, List<RoleBean> roleList) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.isDel = isDel;
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserBean{" + "userId=" + userId + ", userName='" + userName + '\'' + ", userPassword='" + userPassword + '\'' + ", isDel=" + isDel + ", roleList=" + roleList + '}';
    }
}
