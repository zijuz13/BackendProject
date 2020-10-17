package com.huajun123.biz;

import com.huajun123.domain.Project;

import java.util.List;

public interface IProjectsBiz {
    List<Project> getProjectsByCriteria(Integer status,String name);
    void saveProject(Project project);
    void deleteProject(Integer id);
    void updateProject(Project project);
}
