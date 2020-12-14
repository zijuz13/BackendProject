package com.huajun123.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.BlogQuery;
import com.huajun123.domain.SearchRequest;
import com.huajun123.domain.SearchResult;
import com.huajun123.entity.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("projects")
public class SearchController {
    @Autowired
    private ISearchBiz biz;
    private static final ObjectMapper mapper=new ObjectMapper();
    private static final Logger LOGGER= LoggerFactory.getLogger(SearchController.class);
    @PostMapping("all")
    public ResponseEntity<SearchResult> searchProjectsForMyWechatApps(@RequestBody String body){
        BlogQuery query=null;
        SearchResult searchResult = biz.searchResultsForWechat(query=this.wrapObjectFromJson(body));
        LOGGER.info("query {}",query);
        if(Optional.ofNullable(searchResult).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(searchResult);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    private BlogQuery wrapObjectFromJson(String body){
        try {
            BlogQuery query = new BlogQuery();
            Map<String, Object> o = (Map<String, Object>) mapper.readValue(body, new TypeReference<Map<String, Object>>() {
            });
            for (Map.Entry<String, Object> entry : o.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if ("filter".equalsIgnoreCase(key)) {
                    query.setMap((Map<String, List<String>>)value);
                } else {
                    PropertyDescriptor descriptor = new PropertyDescriptor(key, query.getClass());
                    Method writeMethod = descriptor.getWriteMethod();
                    if(Optional.ofNullable(value).isPresent())
                    writeMethod.invoke(query, value);
                    else
                        continue;
                }
            }
            return query;
        }catch (Exception e){
            LOGGER.info("something went wrong {}",e);
            return null;
        }
    }
}
