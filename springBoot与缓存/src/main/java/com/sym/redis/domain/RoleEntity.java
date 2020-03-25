package com.sym.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author shenym
 * @date 2020/3/24 10:24
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable {
    private Integer roleId;
    private String roleName;
    private String roleRemark;
}
