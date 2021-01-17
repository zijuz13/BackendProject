package com.huajun123.biz.impl;
import com.huajun123.biz.IEditBiz;
import com.huajun123.biz.ImageConverter;
import com.huajun123.config.ImageConfigurations;
import com.huajun123.domain.Project;
import com.huajun123.feignclients.ProjectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;


@Service
@EnableConfigurationProperties(ImageConfigurations.class)
public class EditBiz implements IEditBiz {
    @Autowired
    private ImageConfigurations configurations;
    @Autowired
    private ProjectClient projectClient;
    @Autowired
    private ImageConverter converter;
    @Override
    public void addThumbPictureAddressToDataBase(Project project) {
            String video = project.getVideo();
            String absolutePath=configurations.getDockerPath()+"/"+video.substring(video.lastIndexOf("/")+1);
            String convert = converter.convert(absolutePath);
        String visitingAddress=configurations.getLink()+"/"+convert;
            project.setImageUrl(visitingAddress);
            projectClient.updateProject(project);
    }

    @Override
    public void temporyMethodForChangeImageUrl() {
         projectClient.getProjectAkk().forEach(project -> {
             this.addThumbPictureAddressToDataBase(project);
         });
    }
}
