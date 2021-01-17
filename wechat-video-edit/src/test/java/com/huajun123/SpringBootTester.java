package com.huajun123;

import com.huajun123.biz.IEditBiz;
import com.huajun123.config.ImageConfigurations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=EditServiceApplication.class)
@EnableConfigurationProperties(ImageConfigurations.class)
public class SpringBootTester {
    @Autowired
    private ImageConfigurations configurations;
    @Autowired
    private IEditBiz biz;
    @Test
    public void demo1(){
    }
    @Test
    public void demo2(){
        System.out.println(configurations.getDockerPath());
        System.out.println(configurations.getLink());
        System.out.println(configurations.getPath());
    }
}
