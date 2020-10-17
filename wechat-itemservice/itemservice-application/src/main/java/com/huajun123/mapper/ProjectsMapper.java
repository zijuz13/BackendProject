package com.huajun123.mapper;

import com.huajun123.domain.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectsMapper {
    @SelectProvider(type=SqlProvider.class,method="sqlProvider")
    List<Project> getProjects(Map<String,Object> map);
    @Delete("delete from projects where id=#{id}")
    void deleteProject(@Param("id") Integer id);
    @Insert("insert into projects(name,video,description,salepoint,status) values(#{name},#{video},#{description},#{salepoint},#{status})")
    void insertProject(Project project);
   @Update("update projects set name=#{name},video=#{video},description=#{description},salepoint=#{salepoint},status=#{status},imageurl=#{imageUrl} where id=#{id}")
    void updateProject(Project project);
    class SqlProvider{
        public String sqlProvider(Map<String,Object> map) {
            String sqlString="select * from projects where 1=1 ";
            if(null!=map) {
                if (null != map.get("status")) {
                    if (!StringUtils.isEmpty(map.get("status").toString())) {
                        sqlString += "and status=#{status} ";
                    }
                }
                if (null != map.get("name")){
                    if (!StringUtils.isEmpty(map.get("name").toString())) {
                        sqlString += "and name like concat('%',#{name},'%') ";
                    }
            }
            }
            return sqlString;
        }
    }
}
