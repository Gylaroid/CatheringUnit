package ru.catheringunit.entity;

import java.sql.Date;
import java.util.Objects;

public class Proposal {
    private long id;
    private Date date;

    public Proposal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposal proposal = (Proposal) o;
        return id == proposal.id && Objects.equals(date, proposal.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
