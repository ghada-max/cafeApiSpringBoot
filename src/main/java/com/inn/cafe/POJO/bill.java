package com.inn.cafe.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@NamedQuery(name = "bill.getAllBills",query = "select b from bill b order by b.id desc")//where c.id in(select p.category from product p where p.status='true')")
@NamedQuery(name = "bill.getCurrentUserBill",query = "select b from bill b where b.createdby=:username order by b.id desc")//where c.id in(select p.category from product p where p.status='true')")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="bill")
public class bill  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="uuid")
    private String uuid;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="contactnumber")
    private String contactnumber;

    @Column(name="paymentmethod")
    private String paymentmethod;

    @Column(name="total")
    private String total;

    @Column(name="productdetails",columnDefinition = "json")
    private String productdetails;


    @Column(name="createdby")
    private String createdby;

}
