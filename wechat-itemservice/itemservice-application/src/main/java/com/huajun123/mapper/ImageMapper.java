package com.huajun123.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImageMapper {
    @Select("select url from images where status=#{status}")
    List<String> getRollingImages(@Param("status") Integer status);
    @Insert("insert into images(url,status) values(#{url},#{status})")
    void insertImage(Map<String,Object> map1);
    @Delete("delete from images where id=#{id}")
    void deleteImage(@Param("id")Integer id);
}
