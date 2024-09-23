package com.inn.cafe.rest;

import com.inn.cafe.POJO.category;
import com.inn.cafe.utils.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path= "/category")
public interface categoryRest {

        @PostMapping(path ="/add")
        public ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String,String> requestMap);

        @GetMapping(path ="/getUsers")
        public ResponseEntity<List<category>> getAllCategory(@RequestParam(required=false) String filterValue) ;

        @PostMapping(path ="/update")
        ResponseEntity<String> updateCategory(@RequestBody(required=true) Map<String, String>requestMap);
















}
