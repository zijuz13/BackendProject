package com.huajun123.controller;

import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.SearchRequest;
import com.huajun123.domain.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("projects")
public class SearchController {
    @Autowired
    private ISearchBiz biz;
    @PostMapping("all")
    public ResponseEntity<SearchResult> searchProjectsForMyWechatApps(@RequestBody SearchRequest request){
        System.out.println(request);
        SearchResult searchResult = biz.searchResultsForWechat(request);
        if(Optional.ofNullable(searchResult).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(searchResult);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
