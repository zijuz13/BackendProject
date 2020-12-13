package com.huajun123;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@EnableAutoConfiguration
@ComponentScan
@SpringBootConfiguration
@EnableDiscoveryClient
@MapperScan("com.huajun123.mapper")
public class BlogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class,args);
    }
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver webDriver() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        WebDriver webDriver=new RemoteWebDriver(new URL("http://localhost:4444"),capability);
        webDriver.manage().timeouts().implicitlyWait(300L, TimeUnit.SECONDS);
        return webDriver;
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
