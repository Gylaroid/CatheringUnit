package ru.catheringunit.service;

import ru.catheringunit.application.DBWorker;
import ru.catheringunit.dao.MenuElementDAO;
import ru.catheringunit.entity.MenuElement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuElementService implements MenuElementDAO {

    @Override
    public boolean add(MenuElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.menu_elements ( \"food&drinks_id\", menus_id) VALUES (?, ?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getFoodOrDrinkId());
            preparedStatement.setLong(2, entity.getMenuId());
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
    public List<MenuElement> getAll() {
        Connection connection = new DBWorker().getConnection();
        List<MenuElement> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.menus;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            statement.close();
            connection.close();

            if(resultSet != null){
                while(resultSet.next()){
                    MenuElement entity = new MenuElement();
                    entity.setId(resultSet.getLong("id"));
                    entity.setFoodOrDrinkId(resultSet.getLong("food&drinks_id"));
                    entity.setMenuId(resultSet.getLong("menus_id"));
                    result.add(entity);
                }
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public MenuElement getById(long menuId, long foodOrDrinkId) {
        Connection connection = new DBWorker().getConnection();
        MenuElement result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.menu_elements WHERE \"food&drinks_id\" = ? AND menus_id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodOrDrinkId);
            preparedStatement.setLong(2, menuId);
            resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();

            if(resultSet != null) {
                result = new MenuElement();
                result.setId(resultSet.getLong("id"));
                result.setFoodOrDrinkId(resultSet.getLong("food&drinks_id"));
                result.setMenuId(resultSet.getLong("menus_id"));
            }
        } catch (SQLException exc){
            exc.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(MenuElement entity) {
        Connection connection = new DBWorker().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.menu_elements SET \"food&drinks_id\" = ?, menus_id = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getFoodOrDrinkId());
            preparedStatement.setLong(2, entity.getMenuId());
            preparedStatement.setLong(3, entity.getId());
            status = preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean remove(MenuElement entity) {
        Connection connection = new DBWorker().getConnection();
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
