package com.inn.cafe.dao;

import com.inn.cafe.POJO.category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inn.cafe.POJO.product;
import com.inn.cafe.utils.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface productDao extends JpaRepository<product,Integer> {
 List<ProductWrapper> getProducts();
 List<ProductWrapper> findProductById(@Param("id") Integer id);

 @Modifying
 @Transactional
 Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
// Integer getCategory(@Param("status") String status, @Param("id") Integer id);

}
