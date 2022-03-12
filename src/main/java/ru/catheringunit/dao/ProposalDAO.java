package ru.catheringunit.dao;

import ru.catheringunit.entity.Proposal;

import java.util.List;

public interface ProposalDAO {
    //create
    boolean add(Proposal entity);

    //read
    List<Proposal> getAll();

    Proposal getById(long id);

    //update
    boolean update(Proposal entity);

    //delete
    boolean remove(Proposal entity);
}
