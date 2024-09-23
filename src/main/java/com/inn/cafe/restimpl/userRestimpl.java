package com.inn.cafe.restimpl;

import com.inn.cafe.utils.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.inn.cafe.rest.userRest;
import com.inn.cafe.service.userService;
import com.inn.cafe.utils.cafeUtils;
import com.inn.cafe.constants.userConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class userRestimpl implements userRest{
    @Autowired
    userService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String,String> requestMap){
      try{
           return userService.signUp(requestMap);
      }
      catch(Exception ex ){
            ex.printStackTrace();
        }
      return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);


}

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            return userService.getAllUsers();
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateUsers(Map<String, String> requestMap) {
        try{
            return userService.updateUsers(requestMap);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateUsers() {
        try{
            return userService.checkTocken();
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changePassword(requestMap);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            return userService.forgotPassword(requestMap);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
