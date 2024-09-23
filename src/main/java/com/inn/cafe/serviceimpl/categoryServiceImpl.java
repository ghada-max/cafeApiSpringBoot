package com.inn.cafe.serviceimpl;
import com.google.common.base.Strings;
import com.inn.cafe.JWT.JWTfilter;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.dao.categoryDao;
import com.inn.cafe.POJO.category;
import com.inn.cafe.service.categoryService;
import com.inn.cafe.utils.cafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class categoryServiceImpl implements categoryService {
    @Autowired
    categoryDao categoryDao ;



    @Autowired
    JWTfilter JWTfilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            // Check if the user is admin
            if (JWTfilter.isAdmin()) {
                // Validate category data
                if (validateCategoryMap(requestMap, false)) {
                    // Save category to database
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return cafeUtils.getResponseEntity("Category added successfully", HttpStatus.OK);
                } else {
                    // Invalid category data
                    return cafeUtils.getResponseEntity("Invalid category data", HttpStatus.BAD_REQUEST);
                }
            } else {
                // Unauthorized access for non-admin users
                return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            // Log the exception for debugging
            ex.printStackTrace();
            // Return an internal server error response
            return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public ResponseEntity<List<category>> getAllCategory(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                return new ResponseEntity<List<category>>(categoryDao.getAllcategory(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (JWTfilter.isAdmin()) {
                if (validateCategoryMap(requestMap, true)) {
                    Optional<category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (optional.isPresent()) {
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return cafeUtils.getResponseEntity("Category updated successfully", HttpStatus.OK);
                    } else {
                        return cafeUtils.getResponseEntity("Category not found", HttpStatus.NOT_FOUND);
                    }
                } else {
                    return cafeUtils.getResponseEntity("Invalid category data", HttpStatus.BAD_REQUEST);
                }
            } else {
                return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (NumberFormatException ex) {
            return cafeUtils.getResponseEntity("Invalid category ID format", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception for debugging purposes
            return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId)
                return true;
        }
        return false;
    }

    private category getCategoryFromMap(Map<String,String> requestMap,Boolean isAdd)
    {
        category category= new category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }category.setName(requestMap.get("name"));
        return category;

    }



















}