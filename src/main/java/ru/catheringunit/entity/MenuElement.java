package ru.catheringunit.entity;

import java.util.Objects;

public class MenuElement {
    private long id;
    private long recipeId;
    private long menuId;
    private int count;

    public MenuElement() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MenuElement{" +
                "id=" + id +
                ", foodOrDrinkId=" + recipeId +
                ", menuId=" + menuId +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuElement that = (MenuElement) o;
        return id == that.id && recipeId == that.recipeId && menuId == that.menuId && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeId, menuId, count);
    }
}
