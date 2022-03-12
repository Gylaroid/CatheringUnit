package ru.catheringunit.dao;

import ru.catheringunit.entity.Menu;

import java.util.List;

public interface MenuDAO {
    //create
    boolean add(Menu entity);

    //read
    List<Menu> getAll();

    Menu getById(long id);

    //update
    boolean update(Menu entity);

    //delete
    boolean remove(Menu entity);
}
