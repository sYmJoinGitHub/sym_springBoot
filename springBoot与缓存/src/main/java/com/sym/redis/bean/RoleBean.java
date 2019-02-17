package com.sym.redis.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 如果想缓存对象实体类，该对象实体类需要实现 Serializable接口
 *
 * Created by 沈燕明 on 2018/11/22.
 */
public class RoleBean implements Serializable {

    @Setter
    @Getter
    private Integer roleId;
    @Setter
    @Getter
    private String roleName;
    @Setter
    @Getter
    private String roleRemark;

    public RoleBean() {
    }

    public RoleBean(Integer roleId, String roleName, String roleRemark) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleRemark = roleRemark;
    }

    @Override
    public String toString() {
        return "RoleBean{" + "roleId=" + roleId + ", roleName='" + roleName + '\'' + ", roleRemark='" + roleRemark + '\'' + '}';
    }
}
