package com.inn.cafe.restimpl;

import com.inn.cafe.POJO.product;
import com.inn.cafe.service.productService;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.rest.productRest;
import com.inn.cafe.utils.cafeUtils;
import com.inn.cafe.utils.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class productRestImpl implements productRest {
    @Autowired
    productService productService;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
            return productService.addProduct(requestMap);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);



    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProducts() {
        try{
            return productService.getProducts();
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        }
        catch(Exception ex ){
            ex.printStackTrace();
        }
        return null;    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return productService.deleteProduct(id);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateProductStatus(Map<String, String> requestMap) {
        try{
          return  productService.updateProductStatus(requestMap);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return cafeUtils.getResponseEntity(userConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<ProductWrapper>> findProductById(Integer id) {
        try{
            return  productService.findProductById(id);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



}
