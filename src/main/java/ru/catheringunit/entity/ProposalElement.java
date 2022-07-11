package ru.catheringunit.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;

public class ProposalElement {
    private long id;
    private long proposalId;
    private long menuId;
    private long ingredientId;
    private float weight;
    private int count;


    public ProposalElement() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProposalId() {
        return proposalId;
    }

    public void setProposalId(long proposalId) {
        this.proposalId = proposalId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public float getWeight() { return weight; }

    public void setWeight(float weight) { this.weight = weight; }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProposalElement that = (ProposalElement) o;
        return id == that.id && proposalId == that.proposalId && menuId == that.menuId && ingredientId == that.ingredientId && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, proposalId, menuId, ingredientId, count);
    }

    @Override
    public String toString() {
        return "ProposalComposition{" +
                "id=" + id +
                ", proposalId=" + proposalId +
                ", menuId=" + menuId +
                ", ingredientId=" + ingredientId +
                ", count=" + count +
                '}';
    }
}
