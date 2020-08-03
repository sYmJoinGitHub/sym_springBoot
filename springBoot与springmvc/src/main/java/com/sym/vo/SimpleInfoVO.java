package com.sym.vo;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * @author shenyanming
 * @date 2020/8/3 21:47.
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class SimpleInfoVO {

    private int id;
    private String name;
    private List<Object> others;

    public SimpleInfoVO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
