package com.huajun123.biz;

import com.huajun123.entity.Blog;

import java.util.List;

public interface IBlogBiz {
    List<Blog> getBlogsByCriteria(Blog blog);
    void addBlog(Blog blog);
    void deleteBlog(int id);
    void updateBlog(Blog blog);
}
