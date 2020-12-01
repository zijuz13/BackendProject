package com.huajun123.mapper;

import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;
import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PropertyKey;
import java.util.List;
import java.util.Optional;

public interface BlogMapper {
    Logger LOGGER= LoggerFactory.getLogger(BlogMapper.class);
    @SelectProvider(type=SqlProvider.class,method="sqlProvider")
    List<Blog> getBlogsByCriteria(ListQuery blog);
    class SqlProvider{
     public String sqlProvider(ListQuery blog){
        String sqlString="select * from blogs where 1=1 ";
         if(Optional.ofNullable(blog).isPresent()){
          if(!StringUtils.isNullOrEmpty(blog.getTitle())){
              sqlString+="and title like concat('%',#{title},'%') ";
          }
          if(Optional.ofNullable(blog.getImportance()).isPresent()&&blog.getImportance()!=0){
              sqlString+="and importance=#{importance} ";
          }
          if(!StringUtils.isNullOrEmpty(blog.getSort())){
             if("+id".equalsIgnoreCase(blog.getSort())){
                 sqlString+="order by id asc";
             }else{
                 sqlString+="order by id desc";
             }
          }
          if(!StringUtils.isNullOrEmpty(blog.getTime())){
              if(sqlString.contains("order")){
               if("+time".equalsIgnoreCase(blog.getTime())){
                   sqlString+=",displaytime asc";
               }else{
                   sqlString+=",displaytime desc";
               }
              }else{
                  if("+time".equalsIgnoreCase(blog.getTime())){
                      sqlString+="order by displaytime asc";
                  }else{
                      sqlString+="order by displaytime desc";
                  }
              }
          }
              LOGGER.debug("final SQL String is {}",sqlString);
         }
         return sqlString;
     }
    }
    @Insert("insert into blogs(author,content,displaytime,title,commentstatus,importance,image_uri,summary,status) values(#{author},#{content},#{displaytime},#{title},#{commentstatus},#{importance},#{image_uri},#{summary},#{status})")
    @SelectKey(keyColumn = "id",keyProperty = "id",statement = "select last_insert_id()",resultType = int.class, before = false)
    int addBlog(Blog blog);
    @Delete("delete from blogs where id=#{id}")
    int deleteBlog(@Param("id") int id);
    @Update("update blogs set author=#{author},content=#{content},displaytime=#{displaytime},title=#{title},commentstatus=#{commentstatus},importance=#{importance},image_uri=#{image_uri},summary=#{summary},status=#{status} where id=#{id}")
    int updateBlog(Blog blog);
}
