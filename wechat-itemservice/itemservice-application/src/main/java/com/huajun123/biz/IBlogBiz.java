package com.huajun123.biz;

import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;

import java.util.Map;

public interface IBlogBiz {
    Map<String,Object> getBlogsByCriteria(ListQuery blog);
    void addBlog(Blog blog);
    void deleteBlog(int id);
    void updateBlog(Blog blog);
    void updateStatus(String status,int id);
    Blog getBlogById(int id);
}
