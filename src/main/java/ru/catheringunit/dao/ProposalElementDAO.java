package ru.catheringunit.dao;

import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.entity.ProposalElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProposalElementDAO {
    public boolean add(ProposalElement entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.proposal_elements ( proposals_id, menus_id, count) VALUES (?, ?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getProposalId());
            preparedStatement.setLong(2, entity.getMenuId());
            preparedStatement.setInt(3, entity.getCount());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            entity.setId(resultSet.getLong("id"));
            status = true;
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }

    public List<ProposalElement> getAll() {
        Connection connection = new DBConnector().getConnection();
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
                    entity.setCount(resultSet.getInt("count"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public ProposalElement getByIds(long proposalId, long menuId, long ingredientId) {
        Connection connection = new DBConnector().getConnection();
        ProposalElement result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.proposal_elements WHERE proposals_id = ? AND menus_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, proposalId);
            preparedStatement.setLong(2, menuId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null) {
                result = new ProposalElement();
                result.setId(resultSet.getLong("id"));
                result.setProposalId(resultSet.getLong("proposals_id"));
                result.setMenuId(resultSet.getLong("menus_id"));
                result.setCount(resultSet.getInt("count"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public List<ProposalElement> getById(long proposalId) {
        Connection connection = new DBConnector().getConnection();
        List<ProposalElement> results = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.proposal_elements WHERE proposals_id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, proposalId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet != null) {
                while(resultSet.next()){
                    ProposalElement result = new ProposalElement();
                    result.setId(resultSet.getLong("id"));
                    result.setProposalId(resultSet.getLong("proposals_id"));
                    result.setMenuId(resultSet.getLong("menus_id"));
                    result.setCount(resultSet.getInt("count"));
                    results.add(result);
                }

            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return results;
    }

    public boolean update(ProposalElement entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.proposal_elements SET proposals_id = ?, menus_id = ?, count = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getProposalId());
            preparedStatement.setLong(2, entity.getMenuId());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setInt(4, entity.getCount());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean remove(ProposalElement entity) {
        Connection connection = new DBConnector().getConnection();
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
