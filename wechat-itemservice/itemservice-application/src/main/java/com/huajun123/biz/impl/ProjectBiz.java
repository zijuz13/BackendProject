package com.huajun123.biz.impl;

import com.huajun123.biz.IProjectsBiz;
import com.huajun123.domain.Project;
import com.huajun123.mapper.ProjectsMapper;
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
    @Override
    public List<Project> getProjectsByCriteria(Integer status,String name) {
        Map<String,Object> map1=new HashMap<>();
        map1.put("status",status);
        map1.put("name",name);
        return mapper.getProjects(map1);
    }

    @Override
    public void saveProject(Project project) {
    mapper.insertProject(project);
    }

    @Override
    public void deleteProject(Integer id) {
   mapper.deleteProject(id);
    }

    @Override
    public void updateProject(Project project) {
        System.out.println(project);
        mapper.updateProject(project);
    }
}
