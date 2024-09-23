package com.inn.cafe.dao;

import com.inn.cafe.POJO.User;
//import org.apache.catalina.User;
import com.inn.cafe.utils.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface userDao extends JpaRepository<User,Integer> {
    List<UserWrapper> getAllUsers();
    @Transactional
    @Modifying
    Integer updateStatus(@Param("id") Integer id,@Param("status") String status);
    List<String> getAllAdmins();
    User findByEmailId(@Param("email") String email);
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    User findByEmail(String email);
}
