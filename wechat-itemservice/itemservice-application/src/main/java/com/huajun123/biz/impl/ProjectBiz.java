package com.huajun123.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.biz.IProjectsBiz;
import com.huajun123.domain.Project;
import com.huajun123.mapper.ProjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ProjectBiz implements IProjectsBiz {
    @Autowired
    private ProjectsMapper mapper;
    @Autowired
    private AmqpTemplate template;
    private static final Logger LOGGER= LoggerFactory.getLogger(ProjectBiz.class);
    @Override
    public Map<String,Object> getProjectsByCriteria(int page,int limit,Project project) {
        PageHelper.startPage(page,limit);
        List<Project> projects = mapper.getProjects(project);
        PageInfo<Project> info=new PageInfo<>(projects);
        Map<String,Object> map1=new HashMap<>();
        map1.put("items",projects);
        map1.put("total",info.getTotal());
        map1.put("totalPages",info.getPages());
        return map1;
    }

    @Override
    public Project getProjectById(int id) {
        return mapper.getProjectById(id);
    }

    @Override
    public void saveProject(Project project) {
    mapper.insertProject(project);
    LOGGER.info("sent {}",project.getId());
    template.convertAndSend("item.create",project.getId());
    }

    @Override
    public void deleteProject(Integer id) {
   mapper.deleteProject(id);
   template.convertAndSend("item.delete.exchange","item.delete",id);
    }

    @Override
    public void updateProject(Project project) {
        System.out.println(project);
        mapper.updateProject(project);
    }

    @Override
    public void changeStatus(int status, int id) {
        mapper.changeStatus(status,id);
    }
}
