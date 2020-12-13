package com.huajun123.mapper;

import com.huajun123.domain.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectsMapper {
    @SelectProvider(type=SqlProvider.class,method="sqlProvider")
    List<Project> getProjects(Project project);
    @Select("select * from projects where status=1")
    List<Project> getAllProjects();
    @Delete("delete from projects where id=#{id}")
    void deleteProject(@Param("id") Integer id);
    @Insert("insert into projects(name,video,description,salepoint,status) values(#{name},#{video},#{description},#{salepoint},#{status})")
    @SelectKey(keyProperty = "id",keyColumn = "id",before = false,statement = "select last_insert_id()", resultType = Integer.class)
    void insertProject(Project project);
   @Update("update projects set name=#{name},video=#{video},description=#{description},salepoint=#{salepoint},status=#{status},imageurl=#{imageUrl} where id=#{id}")
    void updateProject(Project project);
   @Update("update projects set status=#{status} where id=#{id}")
    void changeStatus(@Param("status") int status,@Param("id") int id);
   @Select("select * from projects where id=#{id}")
   Project getProjectById(@Param("id")int id);
    class SqlProvider{
        public String sqlProvider(Project project) {
            String sqlString="select * from projects where 1=1 ";
            if(null!=project) {
                if (null != project.getStatus()) {
                    if (!StringUtils.isEmpty(project.getStatus())) {
                        sqlString += "and status=#{status} ";
                    }
                }
                if (null != project.getName()){
                    if (!StringUtils.isEmpty(project.getName())) {
                        sqlString += "and name like concat('%',#{name},'%') ";
                    }
            }
            }
            return sqlString;
        }
    }
}
