package com.huajun123.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
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
    public Blog loadJsonToBlog(String body){
        Map<String,Object> map1 = null;
        try {
            map1 = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            LOGGER.error("cannot read value from json file, reasons: {}",e.getMessage());
            return null;
        }
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
