package com.inn.cafe.service;

import com.inn.cafe.utils.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface userService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<UserWrapper>> getAllUsers();
    ResponseEntity<String> updateUsers(Map<String, String> requestMap);
    ResponseEntity<String> checkTocken();
    ResponseEntity<String> changePassword(Map<String, String> requestMap);
    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);




}
