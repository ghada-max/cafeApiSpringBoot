package com.inn.cafe.restimpl;

import com.inn.cafe.constants.userConstants;
import com.inn.cafe.rest.DashboardRest;
import com.inn.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestImpl implements DashboardRest {
    @Autowired
    DashboardService DadhboardService;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        try {
            return DadhboardService.getCount();
        }catch (Exception ex){ex.printStackTrace();}
        return new ResponseEntity(userConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
