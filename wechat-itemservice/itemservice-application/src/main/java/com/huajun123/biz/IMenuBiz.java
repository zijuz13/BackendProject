package com.huajun123.biz;

import com.huajun123.domain.Menu;
import com.huajun123.domain.MenuExtension;

import java.util.List;

public interface IMenuBiz {
    List<Menu> getMenusByCriteira(MenuExtension menu);
    void deleteObject(Integer id);
    void updateObject(Menu menu);
    void insertObject(Menu menu);
}
