package com.inn.cafe.serviceimpl;

import com.inn.cafe.rest.DashboardRest;
import com.inn.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.inn.cafe.dao.categoryDao;
import com.inn.cafe.dao.billDao;
import com.inn.cafe.dao.productDao;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    categoryDao  categoryDao;

    @Autowired
    productDao productDao;

    @Autowired
    billDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() throws Exception
    {

            Map<String, Object> map = new HashMap<>();
            map.put("category", categoryDao.count());
            map.put("product", productDao.count());
            map.put("bills", billDao.count());
            return new ResponseEntity<>(map, HttpStatus.OK);

       }

}
