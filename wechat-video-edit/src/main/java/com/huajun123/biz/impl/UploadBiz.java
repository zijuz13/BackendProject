package com.huajun123.biz.impl;

import com.huajun123.biz.IUploadBiz;
import com.huajun123.config.ImageConfigurations;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@EnableConfigurationProperties(ImageConfigurations.class)
public class UploadBiz implements IUploadBiz {
    @Autowired
    private ImageConfigurations config;
    private Logger LOGGER= LoggerFactory.getLogger(UploadBiz.class);
    @Override
    public Map<String, Object> upload(MultipartFile file) {
        try {
            String replace = UUID.randomUUID().toString().toUpperCase().replace("_", "");
            String newName = replace + "" + file.getOriginalFilename();
                //Use System.getProperty to infer whether or not the application is running on hosts orther than huajun.link host
                String storePath=config.getDockerPath();
                File file1 = new File(storePath);
                if(!file1.exists()){
                    file1.mkdirs();
                }
                file.transferTo(new File(storePath+"/"+newName));
                Map<String,Object> map=new HashMap<>();
                String record=null;
                map.put("files",Maps.newHashMap("file",record=((config.getLink()+"/"+newName))));
                LOGGER.info("image path is {}",record);
                return map;
        }catch (Exception e){
                LOGGER.error("file uploading failed, because {}",e.getMessage());
        }
        return Maps.newHashMap("failMessage","Internal Server Error");
    }
}
