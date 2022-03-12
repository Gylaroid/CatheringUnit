package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.RecipeElementDAO;
import ru.catheringunit.entity.RecipeElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeElementService implements RecipeElementDAO {
    @Override
    public boolean add(RecipeElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.recipes ( \"food&drinks_id\", ingredients_id, weight) VALUES (?, ?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getFoodOrDrinkId());
            preparedStatement.setLong(2, entity.getIngredientId());
            preparedStatement.setInt(3, entity.getWeight());
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
    public List<RecipeElement> getAll() {
        Connection connection = new DBWorker().getConnection();
        List<RecipeElement> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.recipes;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null){
                while(resultSet.next()){
                    RecipeElement entity = new RecipeElement();
                    entity.setId(resultSet.getLong("id"));
                    entity.setFoodOrDrinkId(resultSet.getLong("food&drinks_id"));
                    entity.setIngredientId(resultSet.getLong("ingredients_id"));
                    entity.setWeight(resultSet.getInt("weight"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public RecipeElement getByIds(long foodOrDrinkId, long ingredientId) {
        Connection connection = new DBWorker().getConnection();
        RecipeElement result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.recipes WHERE \"food&drinks_id\" = ? AND ingredients_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodOrDrinkId);
            preparedStatement.setLong(2, ingredientId);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null) {
                result = new RecipeElement();
                result.setId(resultSet.getLong("id"));
                result.setFoodOrDrinkId(resultSet.getLong("food&drinks_id"));
                result.setIngredientId(resultSet.getLong("ingredients_id"));
                result.setWeight(resultSet.getInt("weight"));
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(RecipeElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.recipes SET \"food&drinks_id\" = ?, ingredients_id = ?, weight = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getFoodOrDrinkId());
            preparedStatement.setLong(2, entity.getIngredientId());
            preparedStatement.setInt(3, entity.getWeight());
            preparedStatement.setLong(4, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean remove(RecipeElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.recipes WHERE id = ?";

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
