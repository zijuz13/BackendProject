package com.huajun123.apis;

import com.huajun123.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("projects")
public interface ProjectsApi {
    @DeleteMapping("{id}")
    void deleteProject(@PathVariable("id")Integer id);
    @PostMapping
    void postProject(Project project);
    @PutMapping
    void updateProject(@RequestBody Project project);
    @GetMapping("{id}")
    Project getProjectById(@PathVariable("id")int id);
    @GetMapping("alls")
   List<Project> getProjectAkk();
}
