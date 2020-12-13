package com.huajun123.tests;

import com.huajun123.SearchApplication;
import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.*;
import com.huajun123.entity.Blog;
import com.huajun123.entity.ListQuery;
import com.huajun123.feignclients.BlogClients;
import com.huajun123.feignclients.ProjectsClient;
import com.huajun123.repository.BlogItemRepository;
import com.huajun123.repository.ItemRepository;
import com.huajun123.utils.LoadJsonUtils;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private BlogClients blogClients;
    @Autowired
    private BlogItemRepository repository1;
    @Autowired
    private LoadJsonUtils utils;

    @Test
    public void demo1() {
        SearchRequest request = new SearchRequest();
        request.setName("JSP");
        SearchResult searchResult = biz.searchResultsForWechat(null);
        System.out.println(searchResult);
    }

    @Test
    public void demo2() {
        template.createIndex(Item.class);
        template.putMapping(Item.class);
        List<Item> collect = client.getProjectAkk().stream().map(project -> {
            return biz.buildItemForSearchFromProject(project);
        }).collect(Collectors.toList());
        repository.saveAll(collect);
    }

    @Test
    public void demo3() {
        int page = 1;
        int limit = 5;
//        do {
//            Map<String, Object> blogsByCriteria = blogClients.getBlogsByCriteria(page, limit, "published");
//            List<BlogItem> items = ((List<Map<String,Object>>) blogsByCriteria.get("items")).stream().map(item -> biz.constructBlogItemFromBlog(item)).collect(Collectors.toList());
//            items.forEach(System.out::println);
//            System.out.println(page);
//            page++;
//            limit=items.size();
//        }while(5==limit);
////        System.out.println(blogsByCriteria);
        do {
            Map<String, Object> blogsByCriteria = blogClients.getBlogsByCriteria(page, limit, "published");
            List<Blog> items = ((List<Map<String, Object>>) blogsByCriteria.get("items")).stream().map(item -> this.utils.loadJsonToBlog(item)).collect(Collectors.toList());
            List<BlogItem> collect = items.stream().map(item -> biz.constructBlogItemFromBlog(item)).collect(Collectors.toList());
            collect.forEach(item -> repository1.save(item));
            System.out.println(collect);
            limit = items.size();
            page++;
        } while (5 == limit);
    }

    @Test
    public void demo33() {
        template.createIndex(BlogItem.class);
        template.putMapping(BlogItem.class);
    }

    @Test
    public void demo34() {
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        Iterable<BlogItem> search = repository1.search(matchAllQueryBuilder);
        search.forEach(System.out::println);
    }
    @Test
    public void demo35(){
        template.deleteIndex("web");
    }
}
