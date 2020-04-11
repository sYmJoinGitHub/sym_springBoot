package com.sym.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * @author shenym
 * @date 2020/3/25 10:20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "t_springboot")
public class JpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
}
