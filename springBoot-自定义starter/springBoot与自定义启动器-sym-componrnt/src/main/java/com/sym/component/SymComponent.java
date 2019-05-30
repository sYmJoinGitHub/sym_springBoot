package com.sym.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 模拟：这个是要注入到springBoot IOC容器中的组件
 *
 * Created by 沈燕明 on 2019/5/30 16:35.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SymComponent {
    private Integer id;
    private String name;
}
