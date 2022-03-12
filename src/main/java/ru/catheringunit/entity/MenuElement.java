package ru.catheringunit.entity;

import java.util.Objects;

public class MenuElement {
    private long id;
    private long foodOrDrinkId;
    private long menuId;

    public MenuElement() {
    }

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

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuElement that = (MenuElement) o;
        return id == that.id && foodOrDrinkId == that.foodOrDrinkId && menuId == that.menuId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foodOrDrinkId, menuId);
    }

    @Override
    public String toString() {
        return "MenuComposition{" +
                "id=" + id +
                ", foodOrDrinkId=" + foodOrDrinkId +
                ", menuId=" + menuId +
                '}';
    }
}
