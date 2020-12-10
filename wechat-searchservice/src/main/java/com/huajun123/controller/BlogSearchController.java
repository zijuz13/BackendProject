package com.huajun123.controller;

import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.BlogQuery;
import com.huajun123.domain.BlogResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("blogs")
public class BlogSearchController {
    @Autowired
    private ISearchBiz biz;
    @GetMapping("all")
    public ResponseEntity<BlogResults> searchResults(BlogQuery query){
        return ResponseEntity.status(HttpStatus.OK).body(this.biz.searchBlogs(query));
    }
}
