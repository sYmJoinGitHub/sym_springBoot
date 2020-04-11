package com.sym.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 如果想缓存对象实体类，该对象实体类需要实现 Serializable接口
 *
 * @author shenym
 * @date 2020/3/24 10:23
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private Boolean isDeleted;
    private List<RoleEntity> roleList;
}
