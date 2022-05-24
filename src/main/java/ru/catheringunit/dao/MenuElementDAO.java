package ru.catheringunit.dao;

import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.entity.MenuElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MenuElementDAO {

    public boolean add(MenuElement entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.menu_elements ( \"food&drinks_id\", menus_id, count) VALUES (?, ?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getRecipeId());
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

    public List<MenuElement> getAll() {
        Connection connection = new DBConnector().getConnection();
        List<MenuElement> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.menus;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if(resultSet != null){
                while(resultSet.next()){
                    MenuElement entity = new MenuElement();
                    entity.setId(resultSet.getLong("id"));
                    entity.setRecipeId(resultSet.getLong("food&drinks_id"));
                    entity.setMenuId(resultSet.getLong("menus_id"));
                    entity.setCount(resultSet.getInt("count"));
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

    public MenuElement getByIds(long menuId, long foodOrDrinkId) {
        Connection connection = new DBConnector().getConnection();
        MenuElement result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.menu_elements WHERE \"food&drinks_id\" = ? AND menus_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodOrDrinkId);
            preparedStatement.setLong(2, menuId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null) {
                result = new MenuElement();
                result.setId(resultSet.getLong("id"));
                result.setRecipeId(resultSet.getLong("food&drinks_id"));
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

    public List<MenuElement> getById(long menuId){
        Connection connection = new DBConnector().getConnection();
        List<MenuElement> results = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.menu_elements WHERE menus_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, menuId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet != null) {
                while(resultSet.next()) {
                    MenuElement result = new MenuElement();
                    result.setId(resultSet.getLong("id"));
                    result.setRecipeId(resultSet.getLong("food&drinks_id"));
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

    public boolean update(MenuElement entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.menu_elements SET \"food&drinks_id\" = ?, menus_id = ?, count = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getRecipeId());
            preparedStatement.setLong(2, entity.getMenuId());
            preparedStatement.setInt(3, entity.getCount());
            preparedStatement.setLong(4, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean remove(MenuElement entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.menu_elements WHERE id = ?";

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
