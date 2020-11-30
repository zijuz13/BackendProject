package com.huajun123.biz.impl;

import com.huajun123.biz.IUploadBiz;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadBiz implements IUploadBiz {
    private Logger LOGGER= LoggerFactory.getLogger(UploadBiz.class);
    @Override
    public Map<String, Object> upload(MultipartFile file) {
        try {
            String replace = UUID.randomUUID().toString().toUpperCase().replace("_", "");
            String newName = replace + "" + file.getOriginalFilename();
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(Optional.ofNullable(bufferedImage).isPresent()){
                String storePath="/Library/images";
                File file1 = new File(storePath);
                if(!Optional.ofNullable(file1).isPresent()){
                    file1.mkdirs();
                }
                file.transferTo(new File(storePath+"/"+newName));
                Map<String,Object> map=new HashMap<>();
                map.put("files",Maps.newHashMap("file","http://images.huajun.link"+"/"+newName));
                return map;
            }else{
                return Maps.newHashMap("failMessage","The File uploaded is empty, please try another file");
            }
        }catch (Exception e){
                LOGGER.error("file uploading failed, because {}",e.getMessage());
        }
        return Maps.newHashMap("failMessage","Internal Server Error");
    }
}
