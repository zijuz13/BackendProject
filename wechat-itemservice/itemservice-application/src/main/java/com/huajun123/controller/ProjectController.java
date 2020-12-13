package com.huajun123.controller;

import com.huajun123.biz.IProjectsBiz;
import com.huajun123.domain.Project;
import com.huajun123.mapper.ProjectsMapper;
import com.netflix.ribbon.proxy.annotation.Http;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("projects")
public class ProjectController {
    @Autowired
    private IProjectsBiz biz;
    @Autowired
    private ProjectsMapper mapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(ProjectController.class);
    @GetMapping("all")
    public ResponseEntity<Map<String,Object>> getProjectsByCriteria(int page, int limit, Project project){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(biz.getProjectsByCriteria(page,limit,project));
        }catch (Exception e){
            LOGGER.error("something went wrong {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("alls")
    public ResponseEntity<List<Project>> getALL(){
        return ResponseEntity.status(HttpStatus.OK).body(mapper.getAllProjects());
    }
    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(biz.getProjectById(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id")Integer id){
        try{
            biz.deleteProject(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            LOGGER.error("something went wrong {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> postProject(@RequestBody Project project){
        try{
            biz.saveProject(project);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            LOGGER.error("something went wrong {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("{id}/{status}")
    public ResponseEntity<Void> chanegStatus(@PathVariable("id")int id,@PathVariable("status")int status){
        biz.changeStatus(status,id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateProject(@RequestBody Project project){
        try{
            biz.updateProject(project);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            LOGGER.error("something went wrong {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
