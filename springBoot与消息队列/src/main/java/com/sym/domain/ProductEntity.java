package com.sym.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author shenym
 * @date 2020/3/25 11:26
 */
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private String describe;
    private Boolean isDiscount;
}
