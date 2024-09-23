package com.inn.cafe.POJO;
import com.inn.cafe.utils.wrapper.UserWrapper;




import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")
@NamedQuery(name = "User.findByEmailAndPassword",query = "select u from User u where u.email=:email and u.password=:password")
@NamedQuery(name = "User.getAllUsers",query="select new com.inn.cafe.utils.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'")
@NamedQuery(name = "User.updateStatus",query="update User u set u.status=:status where u.id=:id")
@NamedQuery(name = "User.getAllAdmins",query="select u.email from User u where u.role='admin'")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column (name="email")
    private String email;

    @Column (name="password")
    private String password;

    @Column (name="role")
    private String role;

    @Column (name="status")
    private String status;
}
