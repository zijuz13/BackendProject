package com.huajun123.feignclients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface IconClient {
}
