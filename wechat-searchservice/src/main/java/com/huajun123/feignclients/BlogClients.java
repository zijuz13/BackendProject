package com.huajun123.feignclients;

import com.huajun123.clients.BlogClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface BlogClients extends BlogClient {
}
