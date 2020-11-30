package com.huajun123.fetcher.impl;

import com.huajun123.entity.Blog;
import com.huajun123.fetcher.IFetcherBiz;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class FetchBiz implements IFetcherBiz {
    private WebDriver webDriver;
    @Autowired
    private BeanFactory factory;
    @Autowired
    private StringRedisTemplate template;
    private List<Blog> list=new ArrayList<>();
    @Override
    public List<Blog> fetchBlogsFromNewsWebsite() {
        webDriver= (WebDriver) factory.getBean("webDriver");
        this.getAndSaveFromCNN();
        return null;
    }
    private void getAndSaveFromCNN(){
        webDriver.get("https://www.cnn.com/");
        List<WebElement> article = webDriver.findElements(By.tagName("article"));
        List<Blog> blogs=new ArrayList<>();
        List<String> links=new ArrayList<>();
        for(int i=12;i<=15;i++){
            WebElement webElement = article.get(i);
            WebElement link = webElement.findElement(By.tagName("a"));
            String title = link.getText();
            String href = link.getAttribute("href");
            links.add(href);
            Blog blog=new Blog();
            blog.setTitle(title);
            blogs.add(blog);
        }
        System.out.println(blogs);
        System.out.println(links);
    }
    //根据相应的链接进入具体新闻页面来获取文章内容
    private void cnnContentByLink(String link){
        webDriver.get(link);

    }
    private void getAndSaveFromFoxNews(){

    }
    private void getAndSaveFromOthers(){

    }
}
