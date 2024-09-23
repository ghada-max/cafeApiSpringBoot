package com.inn.cafe.rest;

import com.inn.cafe.utils.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inn.cafe.POJO.product;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/product")
public interface productRest {

    @PostMapping(path="/add")
    ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path="/getProducts")
    ResponseEntity<List<ProductWrapper>> getProducts();

    @PostMapping(path="/updateProduct")
    ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String,String> requestMap);

    @DeleteMapping(path="/deleteProduct/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable  Integer id );

    @PostMapping(path="/updateProductStatus")
    ResponseEntity<String> updateProductStatus(@RequestBody(required=true) Map<String,String> requestMap);

    @GetMapping (path="/GetCategoryById/{id}")
    ResponseEntity<List<ProductWrapper>> findProductById(@PathVariable  Integer id );





}
