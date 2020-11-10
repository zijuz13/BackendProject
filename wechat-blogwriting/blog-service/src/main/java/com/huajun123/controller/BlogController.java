package com.huajun123.controller;

import com.huajun123.biz.IBlogBiz;
import com.huajun123.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("blog")
public class BlogController {
    @Autowired
    private IBlogBiz biz;
  @GetMapping
    public ResponseEntity<List<Blog>> getBlogsByCriteria(Blog blog){
      return ResponseEntity.status(HttpStatus.OK).body(biz.getBlogsByCriteria(blog));
  }
  @PostMapping
    public ResponseEntity<Void> addBlog(Blog blog){
      biz.addBlog(blog);
      return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("id")int id){
      biz.deleteBlog(id);
      return ResponseEntity.status(HttpStatus.OK).build();
  }
  @PutMapping
    public ResponseEntity<Void> updateBlog(Blog blog){
      biz.updateBlog(blog);
      return ResponseEntity.status(HttpStatus.OK).build();
  }
}
