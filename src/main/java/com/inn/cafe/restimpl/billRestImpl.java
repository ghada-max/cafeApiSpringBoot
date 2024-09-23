package com.inn.cafe.restimpl;

import com.inn.cafe.POJO.bill;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.rest.billRest;
import com.inn.cafe.utils.cafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.inn.cafe.service.billService;

import java.util.List;
import java.util.Map;


@RestController
public class billRestImpl implements billRest{

    @Autowired
    billService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
           return billService.generateReport(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteBills(Integer id) {
        try{
            return billService.deleteBills(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<bill>> getbills() {
        try{
            return billService.getbills();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getpdf(Map<String, Object> requestMap) {
          try{
            return billService.getpdf(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


}
