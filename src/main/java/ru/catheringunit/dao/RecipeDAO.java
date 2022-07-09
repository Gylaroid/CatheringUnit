package ru.catheringunit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.entity.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeDAO {
    @Autowired
    public DBConnector dbConnector;

    public boolean add(Recipe entity) {
        boolean status = false;
        Connection connection = dbConnector.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "INSERT INTO public.\"food&drinks\" (category_id, name) VALUES (?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getCategoryId());
            preparedStatement.setString(2, entity.getName());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null){
                entity.setId(resultSet.getLong("id"));
                status = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return status;
    }

    public List<Recipe> getAll() {
        Connection connection = dbConnector.getConnection();
        List<Recipe> foodAndDrinks = new ArrayList<>();
        Statement statement;
        String sql = "SELECT * FROM public.\"food&drinks\";";
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if(resultSet != null) {
                while (resultSet.next()) {
                    Recipe entity = new Recipe();
                    entity.setId(resultSet.getLong("id"));
                    entity.setCategoryId(resultSet.getLong("category_id"));
                    entity.setName(resultSet.getString("name"));
                    foodAndDrinks.add(entity);
                }
            }
            statement.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return foodAndDrinks;
    }

    public Recipe getById(long id) {
        Connection connection = dbConnector.getConnection();
        Recipe recipe = new Recipe();
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.\"food&drinks\" WHERE id = ?;";
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null){
                recipe.setId(resultSet.getLong("id"));
                recipe.setCategoryId(resultSet.getLong("category_id"));
                recipe.setName(resultSet.getString("name"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }


        return recipe;
    }

    public boolean update(Recipe entity) {
        Connection connection = dbConnector.getConnection();
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

    public boolean remove(Recipe entity) {
        Connection connection = dbConnector.getConnection();
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
