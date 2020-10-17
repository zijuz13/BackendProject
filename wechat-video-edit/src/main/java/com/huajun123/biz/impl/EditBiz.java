package com.huajun123.biz.impl;

import com.huajun123.biz.IEditBiz;
import com.huajun123.biz.ImageConverter;
import com.huajun123.domain.Project;
import com.huajun123.feignclients.ProjectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class EditBiz implements IEditBiz {
    @Autowired
    private ProjectClient projectClient;
    @Autowired
    private ImageConverter converter;
    @Override
    public void addThumbPictureAddressToDataBase() {
        List<Project> projectsByCriteria = projectClient.getProjectsByCriteria(1, null);
        projectsByCriteria.forEach(project -> {
            String video = project.getVideo();
            //获取绝对路径,在docker虚拟机下的根目录huanghan
            String absolutePath="/huanghan/"+video.substring(video.lastIndexOf("/")+1);
            String convert = converter.convert(absolutePath);
            String visitingAddress="http://images.huajun123.com/"+convert;
            project.setImageUrl(visitingAddress);
            projectClient.updateProject(project);
        });
    }

    @Override
    public void temporyMethodForChangeImageUrl() {
        List<Project> projectsByCriteria = projectClient.getProjectsByCriteria(1, null);
        projectsByCriteria.forEach(project -> {
            String video = project.getVideo();
            String imageUrl=video.substring(0,video.lastIndexOf("."))+".jpg";
            project.setImageUrl(imageUrl);
            projectClient.updateProject(project);
        });
    }
}
