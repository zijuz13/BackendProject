package com.huajun123.controller;

import com.huajun123.biz.IBlogBiz;
import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;
import com.huajun123.utils.LoadJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("blog")
public class BlogController {
    @Autowired
    private IBlogBiz biz;
    @Autowired
    private LoadJsonUtils utils;
    private static final Logger LOGGER= LoggerFactory.getLogger(BlogController.class);
    //Used Wrapped Instance to transfer conditions for query
  @GetMapping
    public ResponseEntity<Map<String,Object>> getBlogsByCriteria(ListQuery blog){
      return ResponseEntity.status(HttpStatus.OK).body(biz.getBlogsByCriteria(blog));
  }
  //Create Blog Record
  @PostMapping
    public ResponseEntity<Integer> addBlog(@RequestBody String body){
      Blog blog=null;
         biz.addBlog(blog=this.utils.loadJsonToBlog(body));
         LOGGER.info("newly added blog's id is {}",blog.getId());
    return ResponseEntity.status(HttpStatus.OK).body(blog.getId());
  }
  //Delete Blog Record
  @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id")int id){
      biz.deleteBlog(id);
      return ResponseEntity.status(HttpStatus.OK).build();
  }
  //Update Blog Record
  @PutMapping
    public ResponseEntity<Void> updateBlog(@RequestBody String body){
      Blog blog=null;
      biz.updateBlog(blog=this.utils.loadJsonToBlog(body));
      LOGGER.info("updated Blog id is {}",blog.getId());
      return ResponseEntity.status(HttpStatus.OK).build();
  }
  //Update Blog status('published','draft')
  @PutMapping("{id}/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id")int id,@PathVariable("status")String status){
      biz.updateStatus(status,id);
      return ResponseEntity.status(HttpStatus.OK).build();
  }
  @GetMapping("{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("id")int id){
      Blog blogById = biz.getBlogById(id);
      return ResponseEntity.status(HttpStatus.OK).body(blogById);
  }
}
