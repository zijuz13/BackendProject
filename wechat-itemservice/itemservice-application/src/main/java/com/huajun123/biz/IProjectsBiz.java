package com.huajun123.biz;

import com.huajun123.domain.Project;

import java.util.List;
import java.util.Map;

public interface IProjectsBiz {
    Map<String,Object> getProjectsByCriteria(int page, int limit, Project project);
    Project getProjectById(int id);
    void saveProject(Project project);
    void deleteProject(Integer id);
    void updateProject(Project project);
    void changeStatus(int status,int id);
}
