package com.inn.cafe.rest;


import com.inn.cafe.POJO.bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/bill")
public interface billRest {
    @PostMapping(path="/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);

    @DeleteMapping(path="/deleteBill/{id}")
    ResponseEntity<String> deleteBills(@PathVariable Integer id);

    @GetMapping(path="/getBills")
     ResponseEntity<List<bill>> getbills();

    @PostMapping(path="/getPdf")
    ResponseEntity<byte[]> getpdf(@RequestBody Map<String,Object> requestMap);
}
