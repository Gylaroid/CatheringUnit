package ru.catheringunit.entity;

import java.util.Objects;

public class FoodOrDrink {
    private long id;
    private long categoryId;
    private String name;

    public FoodOrDrink() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodOrDrink that = (FoodOrDrink) o;
        return id == that.id && categoryId == that.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId);
    }

    @Override
    public String toString() {
        return "FoodAndDrink{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                '}';
    }
}
