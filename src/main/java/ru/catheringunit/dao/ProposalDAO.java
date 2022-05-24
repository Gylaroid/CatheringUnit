package ru.catheringunit.dao;

import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.entity.Proposal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProposalDAO {
    public boolean add(Proposal entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.proposals (date) VALUES (?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, entity.getDate());
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

    public List<Proposal> getAll() {
        Connection connection = new DBConnector().getConnection();
        List<Proposal> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.proposals;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if(resultSet != null){
                while(resultSet.next()){
                    Proposal entity = new Proposal();
                    entity.setId(resultSet.getLong("id"));
                    entity.setDate(resultSet.getDate("date"));
                    result.add(entity);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public Proposal getById(long id) {
        Connection connection = new DBConnector().getConnection();
        Proposal result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.proposals WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null) {
                result = new Proposal();
                result.setId(resultSet.getLong("id"));
                result.setDate(resultSet.getDate("date"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public boolean update(Proposal entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.proposals SET date = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, entity.getDate());
            preparedStatement.setLong(2, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean remove(Proposal entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.proposals WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }
}
