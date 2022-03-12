package ru.catheringunit.dao;

import ru.catheringunit.entity.FoodOrDrink;

import java.util.List;

public interface FoodOrDrinkDAO {
    //create
    boolean add(FoodOrDrink entity);

    //read
    List<FoodOrDrink> getAll();

    FoodOrDrink getById(long id);

    //update
    boolean update(FoodOrDrink entity);

    //delete
    boolean remove(FoodOrDrink entity);
}
