package com.huajun123;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.huajun123.biz.IPullBiz;
import com.huajun123.biz.impl.PullBiz;
import com.huajun123.domain.ElectionNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PullDataServiceApplication.class)
public class SpringTester {
    @Autowired
    private PullBiz biz;
    @Test
    public void demo1(){
        System.out.println(biz.getInformation());
    }
    @Test
    public void demo2() throws IOException {
        WebClient webClient=new WebClient(BrowserVersion.INTERNET_EXPLORER);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.waitForBackgroundJavaScript(10000);
        Page page = webClient.getPage("http://localhost:9001/services.html");
        HtmlPage page1 = (HtmlPage) page;
        System.out.println(page1.asXml());
    }
    @Test
    public void demo3(){
    }
    @Test
    public void demo4(){
        List<ElectionNews> allElectionNews = biz.getAllElectionNews();
        System.out.println(allElectionNews);
    }
    @Test
    public void demo5(){
//        biz.test();
    }
}
