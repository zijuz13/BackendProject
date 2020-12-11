package com.huajun123.controller;

import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.BlogQuery;
import com.huajun123.domain.BlogResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("blogs")
public class BlogSearchController {
    @Autowired
    private ISearchBiz biz;
    private static final Logger LOGGER= LoggerFactory.getLogger(BlogSearchController.class);
    @PostMapping("all")
    public ResponseEntity<BlogResults> searchResults(HttpServletRequest request,BlogQuery query){
        String parameter = request.getParameter("filter.category");
        if(null==query.getFilter()){
            query.setFilter(new HashMap<>());
        }
        query.getFilter().put("category",parameter);
        LOGGER.info("the filter map is:{}",query.getFilter());
        return ResponseEntity.status(HttpStatus.OK).body(this.biz.searchBlogs(query));
    }
}
