package com.huajun123.controller;

import com.github.pagehelper.PageInfo;
import com.huajun123.biz.IImageBiz;
import com.huajun123.biz.IMenuBiz;
import com.huajun123.domain.Menu;
import com.huajun123.domain.MenuExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class IconController {
    @Autowired
    private IImageBiz biz1;
    @Autowired
    private IMenuBiz biz;
    @GetMapping("all")
    public ResponseEntity<Map<String,Object>> getMenus(MenuExtension menu){
        Map<String,Object> map1=new HashMap<>();
        List<Menu> menus = biz.getMenusByCriteira(menu);
        map1.put("data",menus);
        if(null!=menu.getPage()&&null!=menu.getLimit()){
          PageInfo<Menu> info=new PageInfo<>(menus);
          map1.put("total",info.getTotal());
      }
        return ResponseEntity.status(HttpStatus.OK).body(map1);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteObject(@PathVariable("id")Integer id){
      try{
          biz.deleteObject(id);
          return new ResponseEntity<>(HttpStatus.OK);
      }catch (Exception e){
          return ResponseEntity.badRequest().build();
      }
    }
    @PutMapping
    public ResponseEntity<Void> updateObject(Menu menu){
      try{
          biz.updateObject(menu);
          return ResponseEntity.status(HttpStatus.OK).build();
      }catch (Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
    @PostMapping
    public ResponseEntity<Void> insertObject(Menu menu){
       try{
           biz.insertObject(menu);
           return ResponseEntity.status(HttpStatus.CREATED).build();
       }catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }
    @GetMapping("images/{status}")
    public ResponseEntity<List<String>> getImagesToDisplay(@PathVariable("status") Integer status){
        try {
           return ResponseEntity.status(HttpStatus.OK).body(biz1.getRollingImages(status));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
