package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.CategoryDAO;
import ru.catheringunit.entity.Category;
import ru.catheringunit.entity.Ingredient;
import ru.catheringunit.entity.RecipeElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements CategoryDAO {
    @Override
    public boolean add(Category entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.categories (id, name) VALUES (?, ?);";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            status = preparedStatement.execute();
            preparedStatement.close();
            connection.close();

        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return status;
    }

    @Override
    public List<Category> getAll() {
        Connection connection = new DBWorker().getConnection();
        List<Category> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.categories;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null){
                while(resultSet.next()){
                    Category entity = new Category();
                    entity.setId(resultSet.getLong("id"));
                    entity.setName(resultSet.getString("name"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public Category getById(long id) {
        Connection connection = new DBWorker().getConnection();
        Category result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.categories WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null) {
                result = new Category();
                result.setId(resultSet.getLong("id"));
                result.setName(resultSet.getString("name"));
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Category entity) {
        Connection connection = new DBWorker().getConnection();
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

    @Override
    public boolean remove(Category entity) {
        Connection connection = new DBWorker().getConnection();
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
