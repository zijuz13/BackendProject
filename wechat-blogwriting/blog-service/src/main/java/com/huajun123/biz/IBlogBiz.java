package com.huajun123.biz;

import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;

import java.util.List;
import java.util.Map;

public interface IBlogBiz {
    Map<String,Object> getBlogsByCriteria(ListQuery blog);
    void addBlog(Blog blog);
    void deleteBlog(int id);
    void updateBlog(Blog blog);
}
