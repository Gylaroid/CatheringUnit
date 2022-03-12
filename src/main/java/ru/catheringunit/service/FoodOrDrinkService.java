package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.FoodOrDrinkDAO;
import ru.catheringunit.entity.FoodOrDrink;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodOrDrinkService implements FoodOrDrinkDAO {

    @Override
    public boolean add(FoodOrDrink entity) {
        boolean status = false;
        Connection connection = new DBWorker().getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "INSERT INTO public.\"food&drinks\" (category_id, name) VALUES (?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getCategoryId());
            preparedStatement.setString(2, entity.getName());
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null){
                entity.setId(resultSet.getLong("id"));
                status = true;
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return status;
    }

    @Override
    public List<FoodOrDrink> getAll() {
        Connection connection = new DBWorker().getConnection();
        List<FoodOrDrink> foodAndDrinks = new ArrayList<>();
        Statement statement;
        String sql = "SELECT * FROM public.\"food&drinks\";";
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null) {
                while (resultSet.next()) {
                    FoodOrDrink entity = new FoodOrDrink();
                    entity.setId(resultSet.getLong("id"));
                    entity.setCategoryId(resultSet.getLong("category_id"));
                    entity.setName(resultSet.getString("name"));
                    foodAndDrinks.add(entity);
                }
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return foodAndDrinks;
    }

    @Override
    public FoodOrDrink getById(long id) {
        Connection connection = new DBWorker().getConnection();
        FoodOrDrink foodOrDrink = new FoodOrDrink();
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.\"food&drinks\" WHERE id = ?;";
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null){
                foodOrDrink.setId(resultSet.getLong("id"));
                foodOrDrink.setCategoryId(resultSet.getLong("category_id"));
                foodOrDrink.setName(resultSet.getString("name"));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }


        return foodOrDrink;
    }

    @Override
    public boolean update(FoodOrDrink entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.\"food&drinks\" SET name = ?, category_id = ? WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getCategoryId());
            preparedStatement.setLong(3, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean remove(FoodOrDrink entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.\"food&drinks\" WHERE id = ? AND name = ? AND category_id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setLong(3, entity.getCategoryId());
            status = preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }
}
