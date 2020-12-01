package com.huajun123.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.biz.IBlogBiz;
import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;
import com.huajun123.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BlogBiz implements IBlogBiz {
    @Autowired
    private BlogMapper mapper;
    @Override
    public Map<String,Object> getBlogsByCriteria(ListQuery blog) {
        PageHelper.startPage(blog.getPage(),blog.getLimit());
        List<Blog> blogsByCriteria = mapper.getBlogsByCriteria(blog);
        PageInfo<Blog> info=new PageInfo<>(blogsByCriteria);
        Map<String,Object> map=new HashMap<>();
        map.put("items",blogsByCriteria);
        map.put("total",info.getTotal());
        return map;
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
