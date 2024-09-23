package com.inn.cafe.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "category.getAllcategory",query = "select c from category c ")//where c.id in(select p.category from product p where p.status='true')")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="category")
public class category  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

}
