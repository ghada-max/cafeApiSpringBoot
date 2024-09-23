package com.inn.cafe.service;

import com.inn.cafe.POJO.bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface billService {

    ResponseEntity<String> generateReport(Map<String,Object> requestMap);
    ResponseEntity<List<bill>> getbills();
    ResponseEntity<byte[]> getpdf(Map<String,Object> requestMap);
    ResponseEntity<String> deleteBills(Integer id);
}
