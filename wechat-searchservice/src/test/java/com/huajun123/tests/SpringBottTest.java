package com.huajun123.tests;

import com.huajun123.SearchApplication;
import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.Item;
import com.huajun123.domain.Project;
import com.huajun123.domain.SearchRequest;
import com.huajun123.domain.SearchResult;
import com.huajun123.feignclients.ProjectsClient;
import com.huajun123.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SpringBottTest {
    @Autowired
    private ISearchBiz biz;
    @Autowired
    private ProjectsClient client;
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private ItemRepository repository;
    @Test
    public void demo1(){
        SearchRequest request=new SearchRequest();
        request.setName("JSP");
        SearchResult searchResult = biz.searchResultsForWechat(null);
        System.out.println(searchResult);
    }
    @Test
    public void demo2(){
        template.createIndex(Item.class);
        template.putMapping(Item.class);
        List<Item> collect = client.getProjectsByCriteria(1, null).stream().map(project -> {
            return biz.buildItemForSearchFromProject(project);
        }).collect(Collectors.toList());
        repository.saveAll(collect);
    }
}
