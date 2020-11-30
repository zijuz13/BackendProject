package com.huajun123.biz;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface IUploadBiz {
    Map<String,Object> upload(MultipartFile file) ;
}
