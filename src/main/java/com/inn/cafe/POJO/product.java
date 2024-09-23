package com.inn.cafe.POJO;

import jakarta.persistence.*;
import com.inn.cafe.POJO.category; // Assuming you have a Category class in the same package
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "product.getProducts",query = "select new com.inn.cafe.utils.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.category.id,p.status,p.category.name) from product p")
@NamedQuery(name = "product.updateStatus",query = "update product p set p.status=:status where p.id=:id")
@NamedQuery(name = "product.findProductById",query = "select new com.inn.cafe.utils.wrapper.ProductWrapper(p.name,p.description,p.price) from product p where p.category.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name= "product")

public class product implements Serializable {

    public static final Long SeriaVersionType=123456L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_fx",nullable=false)
    private category category;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private Integer price;

    @Column(name="status")
    private String status;

}
