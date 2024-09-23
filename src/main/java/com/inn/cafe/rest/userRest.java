package com.inn.cafe.rest;

import com.inn.cafe.utils.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
public interface userRest {

    @PostMapping(path ="/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path ="/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path ="/getUsers")
    public ResponseEntity<List<UserWrapper>> getAllUsers() ;

    @PostMapping(path ="/updateUsers")
    public ResponseEntity<String> updateUsers(@RequestBody(required= true) Map<String,String> requestMap);

    @GetMapping(path  ="/checkToken")
     ResponseEntity<String> updateUsers();

    @PostMapping(path ="/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);

    @PostMapping(path ="/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);




}
