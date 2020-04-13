package com.sym.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ym.shen
 * Created on 2020/4/13 11:23
 */
@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_jpa")
public class JpaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double rate;

    private BigDecimal sale;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
