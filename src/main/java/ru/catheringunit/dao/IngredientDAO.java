package ru.catheringunit.dao;

import ru.catheringunit.entity.Ingredient;

import java.util.List;

public interface IngredientDAO{
    //create
    boolean add(Ingredient entity);

    //read
    List<Ingredient> getAll();

    Ingredient getById(long id);

    //update
    boolean update(Ingredient entity);

    //delete
    boolean remove(Ingredient entity);
}
