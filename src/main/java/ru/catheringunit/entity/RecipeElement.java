package ru.catheringunit.entity;

import java.util.Objects;

public class RecipeElement {
    private long id;
    private long foodOrDrinkId;
    private long ingredientId;
    private int weight;

    public RecipeElement() {    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFoodOrDrinkId() {
        return foodOrDrinkId;
    }

    public void setFoodOrDrinkId(long foodOrDrinkId) {
        this.foodOrDrinkId = foodOrDrinkId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeElement recipe = (RecipeElement) o;
        return id == recipe.id && foodOrDrinkId == recipe.foodOrDrinkId && ingredientId == recipe.ingredientId && Float.compare(recipe.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodOrDrinkId, ingredientId, weight);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", foodAndDrinkId=" + foodOrDrinkId +
                ", ingredientId=" + ingredientId +
                ", weight=" + weight +
                '}';
    }
}
