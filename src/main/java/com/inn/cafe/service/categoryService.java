package com.inn.cafe.service;

import com.inn.cafe.POJO.category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface categoryService {

    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<List<category>> getAllCategory(String filterValue);

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
