package ru.catheringunit.dao;

import ru.catheringunit.entity.RecipeElement;

import java.util.List;

public interface RecipeElementDAO {
    //create
    boolean add(RecipeElement entity);

    //read
    List<RecipeElement> getAll();

//    List<RecipeElement> getByFoodOrDrinkId(long foodOrDrinkId);
//
//    List<RecipeElement> getByIngredientId(long ingredientId);

    RecipeElement getByIds(long foodOrDrinkId, long ingredientId);

    //update
    boolean update(RecipeElement entity);

    //delete
    boolean remove(RecipeElement entity);
}
