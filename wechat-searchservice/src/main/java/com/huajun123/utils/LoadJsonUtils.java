package com.huajun123.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huajun123.entity.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class LoadJsonUtils {
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(LoadJsonUtils.class);
    public Blog loadJsonToBlog(Map<String,Object> map1){
        Blog blog=new Blog();
        for (Map.Entry<String, Object> entry : map1.entrySet()) {
            try {
                String key = entry.getKey();
                Object value=entry.getValue();
                PropertyDescriptor descriptor=null;
                if(key.equalsIgnoreCase("display_time")) {
                    descriptor = new PropertyDescriptor("displaytime", Blog.class);
                    Method writeMethod = descriptor.getWriteMethod();
                    writeMethod.invoke(blog,value+"");
                    continue;
                }
                else if(key.equalsIgnoreCase("content_short"))
                    descriptor=new PropertyDescriptor("summary",Blog.class);
                else
                    descriptor=new PropertyDescriptor(key,Blog.class);
                Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(blog,value);
            }catch (Exception e){
                LOGGER.error("{} field cannot be resolved in order to set value into it, detailed messages are: {}",entry.getKey(),e.getMessage());
                return null;
            }
        }
        return blog;
    }
}
