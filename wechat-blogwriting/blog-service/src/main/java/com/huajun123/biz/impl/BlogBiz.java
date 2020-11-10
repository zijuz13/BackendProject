package com.huajun123.biz.impl;

import com.huajun123.biz.IBlogBiz;
import com.huajun123.entity.Blog;
import com.huajun123.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogBiz implements IBlogBiz {
    @Autowired
    private BlogMapper mapper;
    @Override
    public List<Blog> getBlogsByCriteria(Blog blog) {
        return mapper.getBlogsByCriteria(blog);
    }

    @Override
    public void addBlog(Blog blog) {
        mapper.addBlog(blog);
    }

    @Override
    public void deleteBlog(int id) {
       mapper.deleteBlog(id);
    }

    @Override
    public void updateBlog(Blog blog) {
      mapper.updateBlog(blog);
    }
}
