package com.inn.cafe.restimpl;
import com.inn.cafe.POJO.category;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.service.categoryService;

import com.inn.cafe.rest.categoryRest;
import com.inn.cafe.utils.cafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class categoryRestImpl implements categoryRest {
    @Autowired
    categoryService categoryService;


    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {

        try{
           return  categoryService.addNewCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<category>> getAllCategory(String filterValue) {
        try{
            return categoryService.getAllCategory(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
            return categoryService.updateCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.OK);
    }


}
