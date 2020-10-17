package com.huajun123.biz.controller;

import com.huajun123.biz.IJobBiz;
import com.huajun123.biz.IPullBiz;
import com.huajun123.domain.ElectionNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("data")
public class PullDataController {
    private static final Logger LOGGER= LoggerFactory.getLogger(PullDataController.class);
    @Autowired
    private IPullBiz biz;
    @Autowired
    private IJobBiz biz1;
    @GetMapping
    public ResponseEntity<List<ElectionNews>> getData(){
        return ResponseEntity.status(HttpStatus.OK).body(biz.getAllElectionNews());
    }
    @GetMapping("start")
    public ResponseEntity<Void> startJob(){
        try {
            biz1.startFetchingNewsJob();
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            LOGGER.error("asdasd",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
