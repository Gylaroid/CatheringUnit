package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.ProposalDAO;
import ru.catheringunit.entity.Proposal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProposalService implements ProposalDAO {
    @Override
    public boolean add(Proposal entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.proposals (date) VALUES (?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, entity.getDate());
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
    public List<Proposal> getAll() {
        Connection connection = new DBWorker().getConnection();
        List<Proposal> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.proposals;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null){
                while(resultSet.next()){
                    Proposal entity = new Proposal();
                    entity.setId(resultSet.getLong("id"));
                    entity.setDate(resultSet.getDate("date"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public Proposal getById(long id) {
        Connection connection = new DBWorker().getConnection();
        Proposal result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.proposals WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null) {
                result = new Proposal();
                result.setId(resultSet.getLong("id"));
                result.setDate(resultSet.getDate("date"));
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Proposal entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.proposals SET date = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setDate(2, entity.getDate());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean remove(Proposal entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.proposals WHERE id = ?";

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
