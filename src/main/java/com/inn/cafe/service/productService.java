package com.inn.cafe.service;

import org.springframework.http.ResponseEntity;
import com.inn.cafe.POJO.product;
import java.util.List;
import java.util.Map;
import com.inn.cafe.utils.wrapper.ProductWrapper;
public interface productService  {

     ResponseEntity<String> addProduct(Map<String, String> requestMap);
     ResponseEntity<String> updateProduct(Map<String, String> requestMap);
     ResponseEntity<List<ProductWrapper>> getProducts();
     ResponseEntity<String> deleteProduct(Integer id);
     ResponseEntity<String> updateProductStatus(Map<String,String> requestMap);
     ResponseEntity<List<ProductWrapper>> findProductById(Integer id);
}
