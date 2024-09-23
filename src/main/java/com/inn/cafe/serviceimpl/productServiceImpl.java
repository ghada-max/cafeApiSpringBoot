package com.inn.cafe.serviceimpl;

import com.inn.cafe.POJO.product;
import com.inn.cafe.constants.userConstants;
import com.inn.cafe.service.productService;
import com.inn.cafe.utils.cafeUtils;
import com.inn.cafe.utils.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.inn.cafe.dao.productDao;
import com.inn.cafe.JWT.JWTfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.inn.cafe.POJO.category;

@Service
public class productServiceImpl implements  productService {



    @Autowired
    productDao productDao;

    @Autowired
    JWTfilter JWTfilter;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
            if(JWTfilter.isAdmin())
            {
                if(validateProductMap(requestMap,false)){
                    productDao.save(getProductFromMap(requestMap, false));
                    return cafeUtils.getResponseEntity("Product added successfully", HttpStatus.OK);

                }else{
                    return cafeUtils.getResponseEntity(userConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

                }
            }
            else {
                return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null ;

    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        if(JWTfilter.isAdmin()){
           if(validateProductMap(requestMap,true))
           {
               Optional<product> optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
               if(!optional.isEmpty())
               {
                   product product=getProductFromMap(requestMap,true);
                   product.setStatus(optional.get().getStatus());
                   productDao.save(product);
                   return cafeUtils.getResponseEntity("product updated successfuly", HttpStatus.OK);

               }
               else{
                   return cafeUtils.getResponseEntity("product does not exist", HttpStatus.BAD_REQUEST);

               }
           }else
               return cafeUtils.getResponseEntity("product not found", HttpStatus.BAD_REQUEST);

        }else {
            return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        }
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProducts() {
        try{
            return new ResponseEntity<>(productDao.getProducts(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        if (JWTfilter.isAdmin()) {
            Optional<product> optional = productDao.findById(id);
            if (!optional.isEmpty()) { productDao.deleteById(id);
                return cafeUtils.getResponseEntity("product deleted successfully", HttpStatus.OK);

            }
               else
            return cafeUtils.getResponseEntity("product not found", HttpStatus.BAD_REQUEST);

        }else {
            return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        }
    }

    @Override
    public ResponseEntity<String> updateProductStatus(Map<String, String> requestMap) {
        if (JWTfilter.isAdmin()) {
            Optional<product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
            if (!optional.isEmpty()) {
                productDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                return cafeUtils.getResponseEntity("product Status Updated successfully successfully", HttpStatus.OK);

            } else
                return cafeUtils.getResponseEntity("product not found", HttpStatus.BAD_REQUEST);

        } else {
            return cafeUtils.getResponseEntity(userConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        }
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> findProductById(Integer id) {
        try{
            return new ResponseEntity<>(productDao.findProductById(id),HttpStatus.OK);
        }catch(Exception ex )
        {
            ex.printStackTrace();
        }return null;
    }

    private product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
    category category = new category();
    category.setId(Integer.parseInt(requestMap.get("categoryId")));
    product product = new product();
    if(isAdd){
        product.setId(Integer.parseInt(requestMap.get("id")));
    }else{
        product.setStatus("true");
    }
    product.setName(requestMap.get("name"));
    product.setDescription(requestMap.get("description"));
    product.setPrice(Integer.parseInt(requestMap.get("price")));
    product.setCategory(category);
    return product;
}

private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
    if(requestMap.containsKey("name")){
        if(requestMap.containsKey("id")&& validateId)
        {
            return true;
        }else if (!validateId){
            return true;
        }
    }return false;
}


}
