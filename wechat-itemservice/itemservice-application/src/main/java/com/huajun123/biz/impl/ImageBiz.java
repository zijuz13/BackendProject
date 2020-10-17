package com.huajun123.biz.impl;

import com.huajun123.biz.IImageBiz;
import com.huajun123.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class ImageBiz implements IImageBiz{
    private static final List<String> CONTENTTYPES= Arrays.asList("");
    @Autowired
    private ImageMapper mapper;
    @Override
    public List<String> getRollingImages(Integer status) {
        return mapper.getRollingImages(status);
    }

    @Override
    public void deleteImage(Integer id) {
       mapper.deleteImage(id);
    }

    @Override
    public void uploadImage(MultipartFile file) {

    }
}
