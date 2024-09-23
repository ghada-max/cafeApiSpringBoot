package com.inn.cafe.dao;

import com.inn.cafe.POJO.category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface categoryDao extends JpaRepository<category,Integer> {
    List<category> getAllcategory();
}
