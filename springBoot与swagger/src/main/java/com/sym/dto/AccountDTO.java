package com.sym.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenyanming
 * Created on 2020/6/9 14:23
 */
@ApiModel("账户信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("账户信息")
    private String info;

    @ApiModelProperty("是否删除")
    private Boolean isDeleted;

    private static Map<Integer, AccountDTO> map;

    static {
        map = new HashMap<>();
        map.put(1, new AccountDTO(1, "zhangsan", false));
        map.put(2, new AccountDTO(2, "lisi", false));
        map.put(3, new AccountDTO(3, "wangwu", false));
    }

    public static Collection<AccountDTO> list() {
        return map.values();
    }

    public static AccountDTO get(Integer id) {
        return map.get(id);
    }
}
