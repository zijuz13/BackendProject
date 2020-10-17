package com.huajun123.mapper;

import com.huajun123.domain.Menu;
import com.huajun123.domain.MenuExtension;
import org.apache.ibatis.annotations.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

@Mapper
public interface MenuMapper {
    @SelectProvider(type=SqlProvider.class,method="sqlProvider")
    List<Menu> getMenusByCriteria(MenuExtension menuExtension);
    class SqlProvider{
            public String sqlProvider(MenuExtension menuExtension){
                String sqlString="select * from menu where 1=1 ";
                if(null!=menuExtension.getStatus()){
                    sqlString+="and status=#{status} ";
                }
                if(!StringUtils.isEmpty(menuExtension.getName())){
                    sqlString+="and name like concat('%',#{name},'%') ";
                }
                return sqlString;
            }
            public String sqlProvider1(Menu menu){
            String sqlString="update menu set ";
            for(Field f:menu.getClass().getDeclaredFields()){
                f.setAccessible(true);
                if("id".equalsIgnoreCase(f.getName())){
                    continue;
                }
                Object obj= null;
                try {
                    obj = f.get(menu);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(null!=obj){
                    sqlString+=f.getName()+"=#{"+f.getName()+"},";
                }
            }
            sqlString=sqlString.substring(0,sqlString.lastIndexOf(","));
            sqlString+=" where id=#{id}";
            return sqlString;
        }
    }
    @Delete("delete from menu where id=#{id}")
    void deleteObject(@Param("id")Integer id);
    @UpdateProvider(type=SqlProvider.class,method="sqlProvider1")
    void updateObject(Menu menu);
    @Insert("insert into menu(name,icon,status) values(#{name},#{icon},#{status})")
    void saveObject(Menu menu);
}
