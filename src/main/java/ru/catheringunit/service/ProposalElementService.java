package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.ProposalElementDAO;
import ru.catheringunit.entity.ProposalElement;
import ru.catheringunit.entity.RecipeElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProposalElementService implements ProposalElementDAO {
    @Override
    public boolean add(ProposalElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.proposal_elements ( proposals_id, menus_id) VALUES (?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getProposalId());
            preparedStatement.setLong(2, entity.getMenuId());
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            entity.setId(resultSet.getLong("id"));
            status = true;
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }

    @Override
    public List<ProposalElement> getAll() {
        Connection connection = new DBWorker().getConnection();
        List<ProposalElement> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.proposal_elements;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null){
                while(resultSet.next()){
                    ProposalElement entity = new ProposalElement();
                    entity.setId(resultSet.getLong("id"));
                    entity.setProposalId(resultSet.getLong("proposals_id"));
                    entity.setMenuId(resultSet.getLong("menus_id"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public ProposalElement getByIds(long proposalId, long menuId, long ingredientId) {
        Connection connection = new DBWorker().getConnection();
        ProposalElement result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.proposal_elements WHERE proposals_id = ? AND menus_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, proposalId);
            preparedStatement.setLong(2, menuId);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null) {
                result = new ProposalElement();
                result.setId(resultSet.getLong("id"));
                result.setProposalId(resultSet.getLong("food&drinks_id"));
                result.setMenuId(resultSet.getLong("ingredients_id"));
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(ProposalElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.proposal_elements SET proposals_id = ?, menus_id = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getProposalId());
            preparedStatement.setLong(2, entity.getMenuId());
            preparedStatement.setLong(3, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean remove(ProposalElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.proposal_elements WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }
}
