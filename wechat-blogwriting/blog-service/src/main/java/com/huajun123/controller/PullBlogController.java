package com.huajun123.controller;

import com.huajun123.fetcher.IFetcherBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pull")
public class PullBlogController {
    @Autowired
    private IFetcherBiz biz;
    @GetMapping("test")
    public ResponseEntity<Void> testApi(){
        biz.fetchBlogsFromNewsWebsite();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
