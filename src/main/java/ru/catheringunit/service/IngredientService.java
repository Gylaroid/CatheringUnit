package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.IngredientDAO;
import ru.catheringunit.entity.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientService implements IngredientDAO {
    @Override
    public boolean add(Ingredient entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.ingredients ( name, price, count) VALUES (?, ?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setFloat(2, entity.getPrice());
            preparedStatement.setInt(3, 0);
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
    public List<Ingredient> getAll() {
        Connection connection = new DBWorker().getConnection();

        List<Ingredient> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.ingredients;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null){
                while(resultSet.next()){
                    Ingredient entity = new Ingredient();
                    entity.setId(resultSet.getLong("id"));
                    entity.setName(resultSet.getString("name"));
                    entity.setPrice(resultSet.getFloat("price"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public Ingredient getById(long id) {
        Connection connection = new DBWorker().getConnection();
        Ingredient result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.ingredients WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null) {
                result = new Ingredient();
                result.setId(resultSet.getLong("id"));
                result.setName(resultSet.getString("name"));
                result.setPrice(resultSet.getFloat("price"));
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Ingredient entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.ingredients SET name = ?, price = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setFloat(2, entity.getPrice());
            preparedStatement.setLong(3, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean remove(Ingredient entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.ingredients WHERE id = ?";

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
