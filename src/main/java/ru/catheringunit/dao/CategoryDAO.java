package ru.catheringunit.dao;

import ru.catheringunit.entity.Category;

import java.util.List;

public interface CategoryDAO {
    //create
    boolean add(Category entity);

    //read
    List<Category> getAll();

    Category getById(long id);

    //update
    boolean update(Category entity);

    //delete
    boolean remove(Category entity);
}
