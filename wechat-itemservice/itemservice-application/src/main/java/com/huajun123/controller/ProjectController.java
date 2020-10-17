package com.huajun123.controller;

import com.huajun123.biz.IProjectsBiz;
import com.huajun123.domain.Project;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("projects")
public class ProjectController {
    @Autowired
    private IProjectsBiz biz;
    @GetMapping("all/{status}")
    public ResponseEntity<List<Project>> getProjectsByCriteria(@PathVariable("status")Integer status,@RequestParam(value="name",required = false)String name){
        try{
            List<Project> projectsByCriteria = biz.getProjectsByCriteria(status,name);
            return ResponseEntity.status(HttpStatus.OK).body(projectsByCriteria);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id")Integer id){
        try{
            biz.deleteProject(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> postProject(@RequestBody Project project){
        try{
            biz.saveProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping
    public ResponseEntity<Void> updateProject(@RequestBody Project project){
        try{
            biz.updateProject(project);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
