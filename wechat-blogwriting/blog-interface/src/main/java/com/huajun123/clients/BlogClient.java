package com.huajun123.clients;

import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("blog")
public interface BlogClient {
    @GetMapping
    Map<String,Object> getBlogsByCriteria(@RequestParam("page")int page,@RequestParam("limit")int limit,@RequestParam("status")String status);
    @GetMapping("{id}")
    Blog getBlogById(@PathVariable("id") int id);
}
