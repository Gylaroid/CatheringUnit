package ru.catheringunit.dao;

import ru.catheringunit.entity.MenuElement;

import java.util.List;

public interface MenuElementDAO {
    //create
    boolean add(MenuElement entity);

    //read
    List<MenuElement> getAll();

//    List<MenuComposition> getAllByMenuId(long menuId);
//
//    List<MenuComposition> getAllByFoodOrDrinkId(long foodAndDrinkId);

    MenuElement getById(long menuId, long foodOrDrinkId);

    //update
    boolean update(MenuElement entity);

    //delete
    boolean remove(MenuElement entity);
}
