package com.huajun123.biz.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huajun123.biz.IPullBiz;
import com.huajun123.counter.StopCounter;
import com.huajun123.domain.ElectionNews;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import utils.ThreadUtils;

import javax.xml.ws.Action;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PullBiz implements IPullBiz {
    private static final String REDISCACHE="electionNews";
    private static final Logger LOGGER= LoggerFactory.getLogger(PullBiz.class);
    private WebDriver webDriver;
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StringRedisTemplate template;
    @Override
    public String getInformation()  {
        try {
            String url = "https://www.predictit.org";
            Document document = Jsoup.connect(url).get();
            Elements select = document.select("#app > div.app-layout > div:nth-child(2) > div.app-layout__content.app-layout__content--secondary > div > div:nth-child(1) > div.election-map > div:nth-child(1) > div.column.small-12.large-3 > div > div.overview-map__content > a:nth-child(1) > div > div.overview-map-contract__info > span.overview-map-contract__current-price > span > span");
            final StringBuilder builder=new StringBuilder();
            select.forEach(element -> {
                builder.append(element.text());
            });
            return builder.toString();
        }catch (Exception e){
            LOGGER.error("{}",e.getMessage());
            return "fsafsadas";
        }
    }
    @Override
    public String getInformationByDom() {
        try {
            String url = "https://www.predictit.org";
            Document doc = Jsoup.connect(url).get();
            Elements content = doc.getElementsByClass("overview-map__header");
            return this.formAStringByStringBuilder(content);
        }catch (Exception e){
            LOGGER.error("{}",e.getMessage());
            return null;
        }
    }

    @Override
    public String getInformationByHtmlUnit() {

        return null;
    }
//    animated-number__value
    @Override
    public Map<String,String> getPredictitOrgInformation(StopCounter counter) {
        try {
            if(counter.canIStop()){
                return null;
            }
            counter.createCount();
            webDriver.get("https://www.predictit.org");
//            WebDriverWait wait = new WebDriverWait(webDriver, 300);
            By by = By.className("animated-number__value");
//            wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(by)));
            List<WebElement> elements = webDriver.findElements(by);
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < elements.size(); i++) {
                if (0 == i) {
                    map.put("Biden", elements.get(i).getText());
                } else {
                    map.put("Fucker&Sucker Trump", elements.get(i).getText());
                }
            }
            return map;
        }catch (Exception e){
            LOGGER.error("{}",e.getMessage());
            return this.getPredictitOrgInformation(counter);
        }
    }

    private String formAStringByStringBuilder(Elements elements){
        final StringBuilder builder=new StringBuilder();
        elements.forEach(element -> {
            builder.append(element.text());
        });
        return builder.toString();
    }
//"Betting":{
//        "Predictit":90,
//                "Smarkets":90
//    }
//"NationalPolls"{
//        "CNN":{
//
//        }
//    }
    @Override
    public void saveToRedis(){
        webDriver= (WebDriver) beanFactory.getBean("webDriver");
        List<ElectionNews> list=new ArrayList<>();
        ElectionNews news=new ElectionNews();
        news.setBettingOdds(new HashMap<>());
        news.setNationalPolls(new HashMap<>());
        ElectionNews news1=new ElectionNews();
        news1.setNationalPolls(new HashMap<>());
        news1.setBettingOdds(new HashMap<>());
        list.add(news);
        list.add(news1);
        this.loadPredictitInformation(list,new StopCounter());
        this.loadOtherMarketsInformationFromRCP(list,new StopCounter());
        this.loadNationalPollsFromRCP(list,new StopCounter());
        ThreadUtils.execute(() -> {
            try {
                template.opsForValue().set(REDISCACHE, objectMapper.writeValueAsString(list));
            } catch (JsonProcessingException e) {
                LOGGER.error("went wrong because {}",e.getMessage());
            }
        });
        webDriver.quit();
    }
    public List<ElectionNews> getAllElectionNews(){
        String s = template.opsForValue().get(REDISCACHE);
        try {
            if (!StringUtils.isEmpty(s)) {
                return objectMapper.readValue(s, new TypeReference<List<ElectionNews>>() {
                });
            }
            return null;
        }catch (Exception e){
            LOGGER.error("something went wrong because {}",e.getMessage());
            return null;
        }
//        webDriver= (WebDriver) beanFactory.getBean("webDriver");
//        List<ElectionNews> list=new ArrayList<>();
//        ElectionNews news=new ElectionNews();
//        news.setBettingOdds(new HashMap<>());
//        news.setNationalPolls(new HashMap<>());
//        ElectionNews news1=new ElectionNews();
//        news1.setNationalPolls(new HashMap<>());
//        news1.setBettingOdds(new HashMap<>());
//        list.add(news);
//        list.add(news1);
//        this.loadPredictitInformation(list);
//        this.loadOtherMarketsInformationFromRCP(list,new StopCounter());
//        this.loadNationalPollsFromRCP(list,new StopCounter());
//            ThreadUtils.execute(() -> {
//                try {
//                    template.opsForValue().set(REDISCACHE, objectMapper.writeValueAsString(list),5L, TimeUnit.MINUTES);
//                } catch (JsonProcessingException e) {
//                    LOGGER.error("went wrong because {}",e.getMessage());
//                }
//            });
//            webDriver.quit();
    }
    // Load Betting Odds From Predictit Website
    private void loadPredictitInformation(List<ElectionNews> list,StopCounter counter){
        try {
            if(counter.canIStop()){
                return;
            }
            counter.createCount();
            webDriver.get("https://www.predictit.org");
            By by = By.className("animated-number__value");
            List<WebElement> elements = webDriver.findElements(by);
            for(int i=0;i<list.size();i++){
                ElectionNews electionNews = list.get(i);
                String text = elements.get(i).getText();
                electionNews.getBettingOdds().put("Predictit",text.substring(0,text.lastIndexOf("¢")));
                LOGGER.info("The Betting Odds of {} in Predictit Market is {}",i==0?"Biden":"Fucker Trump",elements.get(i).getText());
            }
            return;
        }catch (Exception e){
            LOGGER.error("{}",e.getMessage());
            this.loadPredictitInformation(list,counter);
        }
    }
    // load Betting Odds From RealClearPolitics Website
    private void loadOtherMarketsInformationFromRCP(List<ElectionNews> list,StopCounter counter){
        try{
            if(counter.canIStop()){
                return;
            }
            counter.createCount();
            webDriver.get("https://www.realclearpolitics.com/elections/betting_odds/2020_president/");
            By by=By.xpath("//*[@id=\"polling-data-full\"]/table/tbody/tr");
            List<WebElement> elements = webDriver.findElements(by);
            for(int i=0;i<list.size();i++){
                ElectionNews electionNews = list.get(i);
                for(int j=2;j<elements.size()+1;j++){
                    By by2=By.xpath("//*[@id=\"polling-data-full\"]/table/tbody/tr["+j+"]/td[1]");
                    By by1=By.xpath("//*[@id=\"polling-data-full\"]/table/tbody/tr["+j+"]/td["+(i==0?3:4)+"]");
                    electionNews.getBettingOdds().put((j==2?"Average":webDriver.findElement(by2).getText()),webDriver.findElement(by1).getText());
                }
            }
        }catch (Exception e){
            LOGGER.error("something went wrong, that is {}",e.getMessage());
            this.loadOtherMarketsInformationFromRCP(list,counter);
        }
    }
    private void loadNationalPollsFromRCP(List<ElectionNews> list,StopCounter counter){
        try{
            if(counter.canIStop()){
                return;
            }
            counter.createCount();
            webDriver.get("https://www.realclearpolitics.com/epolls/2020/president/us/general_election_trump_vs_biden-6247.html");
            By by=By.xpath("//*[@id=\"polling-data-rcp\"]/table/tbody/tr");
            List<WebElement> elements = webDriver.findElements(by);
            for(int i=0;i<list.size();i++){
                ElectionNews electionNews = list.get(i);
                for(int j=2;j<elements.size()+1;j++){
                    By by2=By.xpath("//*[@id=\"polling-data-rcp\"]/table/tbody/tr["+j+"]/td[1]");
                    By by1=By.xpath("//*[@id=\"polling-data-rcp\"]/table/tbody/tr["+j+"]/td["+(i==0?5:6)+"]");
                    electionNews.getNationalPolls().put((j==2?"Average":webDriver.findElement(by2).getText()),webDriver.findElement(by1).getText());
                }
            }
        }catch (Exception e){
            LOGGER.error("something went wrong, that is {}",e.getMessage());
            this.loadOtherMarketsInformationFromRCP(list,counter);
        }
    }
}
