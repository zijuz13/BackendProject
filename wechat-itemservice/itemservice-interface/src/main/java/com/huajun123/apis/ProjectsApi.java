package com.huajun123.apis;

import com.huajun123.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("projects")
public interface ProjectsApi {
    @GetMapping("all/{status}")
    List<Project> getProjectsByCriteria(@PathVariable("status")Integer status, @RequestParam(value="name",required = false)String name);
    @DeleteMapping("{id}")
    void deleteProject(@PathVariable("id")Integer id);
    @PostMapping
    void postProject(Project project);
    @PutMapping
    void updateProject(@RequestBody Project project);
}
