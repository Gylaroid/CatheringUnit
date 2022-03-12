package ru.catheringunit.dao;

import ru.catheringunit.entity.ProposalElement;

import java.util.List;

public interface ProposalElementDAO {
    //create
    boolean add(ProposalElement entity);

    //read
    List<ProposalElement> getAll();

//    List<ProposalComposition> getAllByProposalId(long proposalId);
//
//    List<ProposalComposition> getAllByMenuId(long menuId);
//
//    List<ProposalComposition> getAllByIngredientId(long ingredientId);

    ProposalElement getByIds(long proposalId, long menuId, long ingredientId);

    //update
    boolean update(ProposalElement entity);

    //delete
    boolean remove(ProposalElement entity);
}
