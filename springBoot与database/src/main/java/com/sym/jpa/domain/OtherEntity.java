package com.sym.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author shenyanming
 * Created on 2020/6/16 17:03
 */
@Data
@Entity
@Table(name = "t_other")
public class OtherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "jpa_id")
    private long jpaId;

    private String desc;

}
