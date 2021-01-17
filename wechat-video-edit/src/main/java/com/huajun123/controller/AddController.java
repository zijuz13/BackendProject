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

import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("transfer")
public class AddController {
    @Autowired
    private IEditBiz biz;
    //Atomic Class to make this API only invoked once
    private AtomicBoolean isFirst=new AtomicBoolean(true);
    private static final Logger LOGGER= LoggerFactory.getLogger(AddController.class);
    @GetMapping("all")
    public ResponseEntity<Void> transfer(){
        LOGGER.info("Deprecated Method is invoked path mapping: /transfer/all");
        //If the isFirst value is set to false, this will mean that the method has already been invoked.
        if(isFirst.compareAndSet(true,false)) {
            biz.temporyMethodForChangeImageUrl();
            LOGGER.info("This is the first time to initialize the images of all projects");
        }
        else
            LOGGER.info("This method cannot be invoked second time because the screenshots of the first frame" +
                    "of the project have already been initialized");
     return ResponseEntity.status(HttpStatus.OK).build();
    }
}
