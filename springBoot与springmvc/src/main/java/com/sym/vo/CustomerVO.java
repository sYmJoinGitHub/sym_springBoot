package com.sym.vo;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author shenyanming
 * @date 2020/8/3 22:19.
 */
@Data
@Builder
public class CustomerVO {

    @Range(min = 18, max = 66, message = "age只能在[18, 66]之间")
    private int age;

    @NotBlank
    private String name;
}
