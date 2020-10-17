package com.huajun123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootConfiguration
@EnableDiscoveryClient
@EnableFeignClients
public class EditServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EditServiceApplication.class);
    }
}
