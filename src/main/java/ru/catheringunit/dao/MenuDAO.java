package ru.catheringunit.dao;

import org.springframework.stereotype.Component;
import ru.catheringunit.application.DBConnector;
import ru.catheringunit.entity.Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MenuDAO {

    public boolean add(Menu entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO public.menus (name) VALUES (?) RETURNING id;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
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

    public List<Menu> getAll() {
        Connection connection = new DBConnector().getConnection();
        List<Menu> result = new ArrayList<>();
        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM public.menus;";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if(resultSet != null){
                while(resultSet.next()){
                    Menu entity = new Menu();
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

    public Menu getById(long id) {
        Connection connection = new DBConnector().getConnection();
        Menu result = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sql = "SELECT * FROM public.menus WHERE id = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            if(resultSet != null) {
                result = new Menu();
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

    public boolean update(Menu entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "UPDATE public.menus SET name = ? WHERE id = ?";

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

    public boolean remove(Menu entity) {
        Connection connection = new DBConnector().getConnection();
        boolean status = false;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM public.menus WHERE id = ?";

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
