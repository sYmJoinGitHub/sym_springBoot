package com.sym.jpa.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA 映射实体Bean
 *
 * @author ym.shen
 * Created on 2020/4/13 11:23
 */
@Data
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "t_jpa")
@FieldNameConstants
public class JpaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double rate;

    private BigDecimal sale;

    private boolean isDeleted;

    private LocalDateTime createTime;

    /**
     * 关联查询, 一对多映射
     */
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "jpa_id")
    private List<OtherEntity> other = new ArrayList<>();

    /**
     * 使用 spring SpEL 表达式在解析布尔变量时要小心, 默认 SpEL 表达式会用
     * get + 你设置的变量名 + (), 即使用JavaBean方法来获取这个变量。但是Lombok在碰到
     * 布尔变量时, 如果是以 is 作为变量名前缀的, 如本例的 isDeleted, 它只会生成
     * isDeleted()方法; 如果不是以 is 作为变量名前缀的, 例如 right, 它会生成
     * isRight()方法. 这两种方法 SpEL表达式都没办法获取到此变量, 因此很容易抛出：
     * `org.springframework.expression.spel.SpelEvaluationException: EL1008E`异常.
     *
     * 遇到布尔变量, 并且需要使用 SpEL 表达式的, 建议是手写一个set/get方法
     */
    public boolean getIsDeleted() {
        return isDeleted;
    }
}
