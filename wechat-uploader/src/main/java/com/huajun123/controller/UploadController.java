package com.huajun123.controller;

import com.huajun123.biz.IUploadBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private IUploadBiz biz;
    @PostMapping("post")
    public ResponseEntity<Map<String,Object>> uploadFile(MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(biz.upload(file));
    }
}
