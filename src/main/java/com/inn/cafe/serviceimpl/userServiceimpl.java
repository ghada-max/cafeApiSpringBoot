package com.inn.cafe.serviceimpl;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.CustomerUserDetailsService;
import com.inn.cafe.JWT.JWTUtil;
import com.inn.cafe.JWT.JWTfilter;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.POJO.User;
import com.inn.cafe.service.userService;
import com.inn.cafe.utils.Emailtils;
import com.inn.cafe.utils.cafeUtils;
import com.inn.cafe.utils.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.inn.cafe.dao.userDao;
import java.util.*;

@Slf4j
@Service
public class userServiceimpl implements userService {

    @Autowired
    JWTUtil JWTUtil;
    @Autowired
    JWTfilter JWTfilter;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Emailtils Emailtils;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    userDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside sign up: {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromRequestMap(requestMap));
                    return cafeUtils.getResponseEntity(userConstants.USER_SAVED_SUCCESSFULLY, HttpStatus.OK);
                } else {
                    return cafeUtils.getResponseEntity(userConstants.FOUND_EMAIL, HttpStatus.BAD_REQUEST);
                }
            } else {
                return cafeUtils.getResponseEntity(userConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            log.error("Error occurred during sign up", ex);
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                String userStatus = customerUserDetailsService.getUserDetail().getStatus();
                log.info("User status: {}", userStatus); // Log the status for debugging

                if ("true".equalsIgnoreCase(userStatus)) {
                    // Uncomment this to generate and return the JWT token
                    String token = JWTUtil.generateToken(
                       customerUserDetailsService.getUserDetail().getEmail(),
                       customerUserDetailsService.getUserDetail().getRole()
                    );
                   return new ResponseEntity<String>("{\"token\":\"" + token + "\"}", HttpStatus.OK);

                 //   return new ResponseEntity<String>("User logged Successfully! status=true", HttpStatus.OK);
                } else if (userStatus == null) {
                    return new ResponseEntity<String>("status=null Wait for admin Approval", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("Error occurred during login", ex);
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            if(JWTfilter.isAdmin()){
                return new ResponseEntity<> (userDao.getAllUsers(),HttpStatus.OK);
            }
        }catch(Exception ex){}
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUsers(Map<String, String> requestMap) {
     try{
         if(JWTfilter.isAdmin()){
             Optional<User> optional=userDao.findById(Integer.parseInt(requestMap.get("id")));
             if(!optional.isEmpty()) {
                 userDao.updateStatus(Integer.valueOf(requestMap.get("id")),requestMap.get("status"));
               //  sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmins());
                 return cafeUtils.getResponseEntity(userConstants.USER_UPDATED_SUCCESSFULLY, HttpStatus.OK);
             }else{
                 return cafeUtils.getResponseEntity(userConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

             }

        }else{
            return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.BAD_REQUEST);

        }}catch (Exception ex )
     {
         ex.printStackTrace();
     }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> checkTocken() {
        return cafeUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
             User userObj = userDao.findByEmail(JWTfilter.getCurrentUser());
             if(!userObj.equals(null)){
                 if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                      userObj.setPassword(requestMap.get("newPassword"));
                      userDao.save(userObj);
                      return cafeUtils.getResponseEntity("password updated successfully",HttpStatus.OK);
                 }                      return cafeUtils.getResponseEntity("incorrect old password",HttpStatus.BAD_REQUEST);

             }
        }catch (Exception ex){ex.printStackTrace();}
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
            }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User user = userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                Emailtils.forgotMail(user.getEmail(),"credential cafe management system", user.getPassword());
                return cafeUtils.getResponseEntity("check your email for credentials",HttpStatus.OK);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmins) {
       allAdmins.remove(JWTfilter.getCurrentUser());
       if(status != null && status.equalsIgnoreCase("true")){
           Emailtils.sendSimpleMessage(JWTfilter.getCurrentUser(),"Account approved","user:-"+user+"\n is aaproved"+JWTfilter.getCurrentUser(),allAdmins);
       }
           else{           Emailtils.sendSimpleMessage(JWTfilter.getCurrentUser(),"Account disabled","user:-"+user+"\n is disabled"+JWTfilter.getCurrentUser(),allAdmins);
       }
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
    }

    private User getUserFromRequestMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setRole(requestMap.get("role"));
        user.setStatus(requestMap.get("status"));
        return user;
    }
}
