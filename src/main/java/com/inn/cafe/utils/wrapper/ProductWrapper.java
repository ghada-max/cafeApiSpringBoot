package com.inn.cafe.utils.wrapper;

import lombok.Data;
import com.inn.cafe.POJO.category;
@Data
public class ProductWrapper {

    Integer id;
    String name;
    String description;
    Integer price;
    String categoryName;
    Integer categoryId;
    String status;

    public ProductWrapper(Integer id,String name, String description, Integer price,Integer categoryId,String status,String categoryName ){
        this.id=id;
        this.name=name;
        this.description=description;
        this.price=price;
        this.status=status;
        this.categoryId=categoryId;
        this.categoryName=categoryName;
    }
    public ProductWrapper(String name, String description, Integer price){

        this.name=name;
        this.description=description;
        this.price=price;

    }
}
