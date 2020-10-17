package com.huajun123.feignclients;

import com.huajun123.apis.ProjectsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface ProjectsClient extends ProjectsApi {
}
