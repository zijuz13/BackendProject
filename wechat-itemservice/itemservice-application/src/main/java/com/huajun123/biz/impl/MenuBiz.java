package com.huajun123.biz.impl;

import com.github.pagehelper.PageHelper;
import com.huajun123.biz.IMenuBiz;
import com.huajun123.domain.Menu;
import com.huajun123.domain.MenuExtension;
import com.huajun123.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class MenuBiz implements IMenuBiz {
    @Autowired
    private MenuMapper mapper;
    @Override
    public List<Menu> getMenusByCriteira(MenuExtension menu) {
        if(null!=menu){
            if(null!=menu.getPage()&&null!=menu.getLimit()){
                PageHelper.startPage(menu.getPage(),menu.getLimit());
            }
            return mapper.getMenusByCriteria(menu);
        }
        return null;
    }

    @Override
    public void deleteObject(Integer id) {
     mapper.deleteObject(id);
    }

    @Override
    public void updateObject(Menu menu) {
     mapper.updateObject(menu);
    }

    @Override
    public void insertObject(Menu menu) {
     mapper.saveObject(menu);
    }
}
