package com.huajun123.mapper;

import com.huajun123.entity.Blog;
import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

public interface BlogMapper {
    @SelectProvider(type=SqlProvider.class,method="sqlProvider")
    List<Blog> getBlogsByCriteria(Blog blog);
    class SqlProvider{
     public String sqlProvider(Blog blog){
         String sqlString="select * from blogs where 1=1 ";
         if(Optional.ofNullable(blog).isPresent()){
          if(!StringUtils.isNullOrEmpty(blog.getTitle())){
              sqlString+="and title like concat('%',#{title},'%') ";
          }
         }
         return sqlString;
     }
    }
    @Insert("insert into blogs(author,content,createtime,title) values(#{author},#{content},#{createtime},#{title})")
    int addBlog(Blog blog);
    @Delete("delete from blogs where id=#{id}")
    int deleteBlog(@Param("id") int id);
    @Update("update blogs set author=#{author},content=#{content},createtime=#{createtime},title=#{title} where id=#{id}")
    int updateBlog(Blog blog);
}