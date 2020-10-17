package com.huajun123.apis;

import com.huajun123.domain.Menu;
import com.huajun123.domain.MenuExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("menus")
public interface MenuApi {
    @GetMapping("all")
    Map<String,Object> getMenus(MenuExtension menu);
    @DeleteMapping("{id}")
    void deleteObject(@PathVariable("id")Integer id);
    @PutMapping
    void updateObject(Menu menu);
    @PostMapping
    void insertObject(Menu menu);
    @GetMapping("images/{status}")
    List<String> getImagesToDisplay(@PathVariable("status") Integer status);
}
