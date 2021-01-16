package com.huajun123.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.biz.IBlogBiz;
import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;
import com.huajun123.mapper.BlogMapper;
import com.huajun123.utils.ThreadUtils;
import org.springframework.amqp.core.AmqpTemplate;
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
    @Autowired
    private AmqpTemplate template;
    @Override
    public Map<String,Object> getBlogsByCriteria(ListQuery blog) {
        PageHelper.startPage(blog.getPage(),blog.getLimit());
        List<Blog> blogsByCriteria = mapper.getBlogsByCriteria(blog);
        PageInfo<Blog> info=new PageInfo<>(blogsByCriteria);
        Map<String,Object> map=new HashMap<>();
        map.put("items",blogsByCriteria);
        map.put("total",info.getTotal());
        map.put("totalPages",info.getPages());
        return map;
    }

    @Override
    public void addBlog(Blog blog) {
        mapper.addBlog(blog);
        ThreadUtils.execute(()->{
          createMessage(blog.getId(),"create");
        });
    }

    @Override
    public void deleteBlog(int id) {
       mapper.deleteBlog(id);
        ThreadUtils.execute(()->{
         this.createMessage(id,"delete");
        });
    }

    @Override
    public void updateBlog(Blog blog) {
      mapper.updateBlog(blog);
        ThreadUtils.execute(()->{
         this.createMessage(blog.getId(),"update");
        });
    }

    @Override
    public void updateStatus(String status, int id) {
        mapper.updateStatus(status,id);
    }

    @Override
    public Blog getBlogById(int id) {
        return mapper.getBlogById(id);
    }
    private void createMessage(int id,String type){
     template.convertAndSend("es.blog.exchange",type,id);
    }
}
