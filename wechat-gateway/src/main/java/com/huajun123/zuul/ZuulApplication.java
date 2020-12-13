package com.huajun123.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    private static final Logger LOGGER= LoggerFactory.getLogger(ZuulApplication.class);
    public static void main(String[] args) {
        LOGGER.info(System.getProperty("profile"));
        LOGGER.info(System.getProperty("eureka.client.service-url.defaultZone"));
        SpringApplication.run(ZuulApplication.class);
    }
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config=new CorsConfiguration();
        config.addAllowedOrigin("https://www.huajun.link");
        config.addAllowedOrigin("http://manage.huajun.link");
        config.addAllowedOrigin("http://manage.huajun123.com");
        config.addAllowedOrigin("http://www.huajun123.com");
        config.addAllowedOrigin("http://localhost:9001");
        config.addAllowedOrigin("http://localhost:9530");
        config.setAllowCredentials(true);
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
