package com.inn.cafe.dao;
import com.inn.cafe.POJO.bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface billDao extends JpaRepository <bill,Integer> {
    List<bill>  getAllBills();
    List<bill>  getCurrentUserBill(@Param ("username") String username);
}
