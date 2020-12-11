package com.huajun123.biz.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huajun123.biz.ISearchBiz;
import com.huajun123.domain.*;
import com.huajun123.entity.Blog;
import com.huajun123.repository.BlogItemRepository;
import com.huajun123.repository.ItemRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.apache.lucene.search.BooleanQuery;
import org.apache.tomcat.util.buf.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchBiz implements ISearchBiz {
    private static final String FRONTEND="frontendgroup";
    private static final String FRAMEWORK="frameworkgroup";
    private static final String PERSISTENCY="persistencygroup";
    private static final String CATEGORYGROUP="categorygroup";
    @Autowired
    private ObjectMapper mapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(SearchBiz.class);
    @Autowired
    private ItemRepository repository;
    @Autowired
    private BlogItemRepository blogItemRepository;
    @Override
    public Item buildItemForSearchFromProject(Project project) {
        Item item=new Item();
        item.setId(Long.parseLong(project.getId()+""));
        item.setName(project.getName());
        item.setDescription(project.getDescription());
        item.setVideo(project.getVideo());
        item.setImage(project.getImageUrl());
        Map<String,List<Object>> o = null;
        try {
            o = mapper.readValue(project.getSalepoint(), new TypeReference<Map<String, List<Object>>>() {
            });
        } catch (IOException e) {
            LOGGER.error("JSON解析出错了，{}",e);
        }
        item.setMap(o);
        if(null!=o){
            String framework = StringUtils.join(o.get("framework").stream().map(f -> f.toString()).collect(Collectors.toList()),'+');
            item.setSalepoint(framework);
        }
        return item;
    }

    @Override
    public SearchResult searchResultsForWechat(SearchRequest request) {
        NativeSearchQueryBuilder builder=new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = this.getBoolQueryBuilder(request);
        builder.withQuery(boolQueryBuilder);
        builder.withSourceFilter(new FetchSourceFilter(null,new String[]{"map"}));
        Page<Item> search = this.repository.search(builder.build());
        Map<String, List<String>> searchAggregation = this.getSearchAggregation(boolQueryBuilder);
        return new SearchResult(search.getContent(),searchAggregation);
    }

    @Override
    public BlogItem constructBlogItemFromBlog(Blog blog) {
        List<Object> list= null;
        try {
            list = this.mapper.readValue(blog.getCategory(),new TypeReference<List<Object>>(){});
        } catch (IOException e) {
            this.LOGGER.error("ObjectMapper failed to read Value From category json string,cause: {}",e.getMessage());
            return null;
        }
        //users can query blog's title, content_short, summary information
        String all=blog.getAuthor()+" "+blog.getSummary()+" "+blog.getTitle();
        BlogItem blogItem=new BlogItem();
        blogItem.setAuthor(blog.getAuthor());
        blogItem.setCommentstatus(blog.getCommentstatus());
        blogItem.setDisplay_time(blog.getDisplaytime());
        blogItem.setId(Long.parseLong(blog.getId()+""));
        blogItem.setImage_uri(blog.getImage_uri());
        blogItem.setContent_short(blog.getSummary());
        blogItem.setTitle(blog.getTitle());
        blogItem.setAll(all);
        Map<String,Object> map=new HashMap<>();
        map.put("category",list);
        blogItem.setSpecs(map);
        return blogItem;
    }

    @Override
    public BlogResults searchBlogs(BlogQuery query) {
        NativeSearchQueryBuilder builder=new NativeSearchQueryBuilder();
//        builder.withSourceFilter(new FetchSourceFilter(new String[]{""},new String[]{"all","specs"}));
        //ADD search query to form a bool query json string
        builder.withQuery(this.constructBlogBoolQuerybuilder(QueryBuilders.boolQuery(),query));
        builder.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
        this.addAggregationCategoryIntoResults(builder);
        builder.withPageable(PageRequest.of(query.getPage()-1,query.getLimit()));
        Page<BlogItem> search = blogItemRepository.search(builder.build());
        //将聚合结果封装进入结果包装实体
        return this.encapsulateSearchResultsWithAggregation(((AggregatedPage<BlogItem>)search).getAggregation(CATEGORYGROUP),new BlogResults(search.getContent(),Integer.parseInt(search.getTotalElements()+""),search.getTotalPages()));
    }
    private BoolQueryBuilder constructBlogBoolQuerybuilder(BoolQueryBuilder builder,BlogQuery query){
        if(org.apache.commons.lang.StringUtils.isEmpty(query.getName()))
            builder.must(QueryBuilders.matchAllQuery());
        else
        builder.must(QueryBuilders.matchQuery("all",query.getName()));
        if(Optional.ofNullable(query.getFilter()).isPresent()){
            Map<String,String> map=query.getFilter();
            if(!org.apache.commons.lang.StringUtils.isEmpty(map.get("category"))){
                String[] category = map.get("category").split(",");
                for(String c:category){
                    builder.filter(QueryBuilders.termQuery("specs.category.keyword",c));
                }
            }
        }
        return builder;
    }
    private BlogResults encapsulateSearchResultsWithAggregation(Aggregation aggregation,BlogResults results){
        if(aggregation instanceof StringTerms){
            Map<String,Object> map=new HashMap<>();
            List<StringTerms.Bucket> buckets = ((StringTerms) aggregation).getBuckets();
            buckets.forEach(bucket -> {
                map.put(bucket.getKeyAsString(),bucket.getDocCount());
            });
            results.setAggs(map);
        }else{
            LOGGER.warn("aggregation is not StringTerms instance");
        }
        return results;
    }
    private void addAggregationCategoryIntoResults(NativeSearchQueryBuilder builder){
        builder.addAggregation(AggregationBuilders.terms(CATEGORYGROUP).field("specs.category.keyword"));
    }
    private BoolQueryBuilder getBoolQueryBuilder(SearchRequest request){
        BoolQueryBuilder builder= QueryBuilders.boolQuery();
        if(null!=request) {
            if (!org.apache.commons.lang.StringUtils.isEmpty(request.getName())) {
              builder.must(QueryBuilders.matchQuery("name",request.getName()).operator(Operator.OR));
            }else{
                builder.must(QueryBuilders.matchAllQuery());
            }
            if(null!=request.getFilter()){
                Map<String, String> filter = request.getFilter();
                for(Map.Entry<String,String> map1:filter.entrySet()){
                    String key = map1.getKey();
                    String value = map1.getValue();
                    builder.filter(QueryBuilders.termQuery("map."+key+".keyword",value));
                }
            }
        }else{
            builder.must(QueryBuilders.matchAllQuery());
        }
        return builder;
    }
    private Map<String,List<String>> getSearchAggregation(BoolQueryBuilder builder1){
        final Map<String,List<String>> map1=new HashMap<>();
        Optional.ofNullable(builder1).ifPresent(builder->{
            NativeSearchQueryBuilder query=new NativeSearchQueryBuilder();
            query.withQuery(builder);
            query.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
            query.addAggregation(AggregationBuilders.terms(FRONTEND).field("map.frontend.keyword"));
            query.addAggregation(AggregationBuilders.terms(FRAMEWORK).field("map.framework.keyword"));
            query.addAggregation(AggregationBuilders.terms(PERSISTENCY).field("map.persistency.keyword"));
            List<String> list1= Arrays.asList(FRONTEND,FRAMEWORK,PERSISTENCY);
            Page<Item> search = this.repository.search(query.build());
            AggregatedPage<Item> search1 = (AggregatedPage<Item>) search;
            list1.forEach(s->{
                Aggregation aggregation = search1.getAggregation(s);
                StringTerms aggregation1 = (StringTerms) aggregation;
                List<String> collect = aggregation1.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString).collect(Collectors.toList());
                if(FRONTEND.equalsIgnoreCase(s)){
                    map1.put("前端",collect);
                }else if(FRAMEWORK.equalsIgnoreCase(s)){
                    map1.put("框架",collect);
                }else{
                   map1.put("持久层",collect);
                }
            });
        });
           return map1;
    }
}
