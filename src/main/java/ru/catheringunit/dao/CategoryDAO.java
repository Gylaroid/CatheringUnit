package ru.catheringunit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.application.DBInitializer;
import ru.catheringunit.entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryDAO {
    @Autowired
    public DBConnector dbConnector;

    public boolean add(Category entity) {
        Connection connection = dbConnector.getConnection();
        boolean status = false;
        ResultSet resultSet;

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.categories (name) VALUES (?) RETURNING id;";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            resultSet = preparedStatement.executeQuery();
            if(resultSet != null){
                resultSet.next();
                entity.setId(resultSet.getLong("id"));
                status = true;
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }

    public List<Category> getAll() {
        Connection connection = dbConnector.getConnection();
        List<Category> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.categories;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if(resultSet != null){
                while(resultSet.next()){
                    Category entity = new Category();
                    entity.setId(resultSet.getLong("id"));
                    entity.setName(resultSet.getString("name"));
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

    public Category getById(long id) {
        Connection connection = dbConnector.getConnection();
        Category result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.categories WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null) {
                result = new Category();
                result.setId(resultSet.getLong("id"));
                result.setName(resultSet.getString("name"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public boolean update(Category entity) {
        Connection connection = dbConnector.getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.categories SET name = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean remove(Category entity) {
        Connection connection = dbConnector.getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.categories WHERE id = ?";

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
