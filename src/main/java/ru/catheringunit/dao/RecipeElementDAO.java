package ru.catheringunit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.entity.Ingredient;
import ru.catheringunit.entity.RecipeElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeElementDAO {
    @Autowired
    public DBConnector dbConnector;

    public boolean add(RecipeElement entity) {
        Connection connection = dbConnector.getConnection();
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

    public List<RecipeElement> getAll() {
        Connection connection = dbConnector.getConnection();
        List<RecipeElement> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.recipes;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

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
            statement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public RecipeElement getByIds(long foodOrDrinkId, long ingredientId) {
        Connection connection = dbConnector.getConnection();
        RecipeElement result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.recipes WHERE \"food&drinks_id\" = ? AND ingredients_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodOrDrinkId);
            preparedStatement.setLong(2, ingredientId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null) {
                result = new RecipeElement();
                result.setId(resultSet.getLong("id"));
                result.setFoodOrDrinkId(resultSet.getLong("food&drinks_id"));
                result.setIngredientId(resultSet.getLong("ingredients_id"));
                result.setWeight(resultSet.getInt("weight"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    public List<RecipeElement> getById(long foodOrDrinkId){
        Connection connection = dbConnector.getConnection();
        List<RecipeElement> recipeElements = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.recipes WHERE \"food&drinks_id\" = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodOrDrinkId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet != null) {
                while(resultSet.next()){
                    RecipeElement result = new RecipeElement();
                    result.setId(resultSet.getLong("id"));
                    result.setFoodOrDrinkId(resultSet.getLong("food&drinks_id"));
                    result.setIngredientId(resultSet.getLong("ingredients_id"));
                    result.setWeight(resultSet.getInt("weight"));
                    recipeElements.add(result);
                }

            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return recipeElements;
    }

    public List<Ingredient> getIngredientsByRecipeId(long foodOrDrinkId){
        Connection connection = dbConnector.getConnection();
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT ingredients.id, ingredients.name, ingredients.price FROM public.ingredients, public.recipes WHERE ingredients.id = recipes.ingredients_id AND recipes.\"food&drinks_id\" = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodOrDrinkId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet != null) {
                while(resultSet.next()){
                    Ingredient result = new Ingredient();
                    result.setId(resultSet.getLong("id"));
                    result.setName(resultSet.getString("name"));
                    result.setPrice(resultSet.getFloat("price"));
                    ingredients.add(result);
                }

            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return ingredients;
    }

    public boolean update(RecipeElement entity) {
        Connection connection = dbConnector.getConnection();
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

    public boolean remove(RecipeElement entity) {
        Connection connection = dbConnector.getConnection();
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
