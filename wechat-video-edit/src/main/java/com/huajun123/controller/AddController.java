package com.huajun123.controller;

import com.huajun123.biz.IEditBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("transfer")
public class AddController {
    @Autowired
    private IEditBiz biz;
    private static final Logger LOGGER= LoggerFactory.getLogger(AddController.class);
    @GetMapping("all")
    public ResponseEntity<Void> transfer(){
        try{
            biz.addThumbPictureAddressToDataBase();
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            LOGGER.error("cannot transfer all movies to thumbs cause: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
