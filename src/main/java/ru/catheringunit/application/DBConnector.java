package ru.catheringunit.application;

import ru.catheringunit.entity.FieldMeta;
import ru.catheringunit.entity.TableMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private final String HOST = "";
    private final String USER = "";
    private final String PASSWORD = "";

    private Connection connection;


    public DBConnector(){    }


    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    public List<TableMeta> getTablesMeta(){
        getConnection();
        List<TableMeta> tablesList = new ArrayList<>();

        ResultSet tablesSet;
        ResultSet fieldsSet;

        String getTablesSQL = "SELECT table_name FROM information_schema.tables WHERE table_schema NOT IN ('information_schema','pg_catalog');";
        String getFieldsSQL = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?;";

        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(getFieldsSQL);

            tablesSet = statement.executeQuery(getTablesSQL);

            while(tablesSet.next()){
                TableMeta table = new TableMeta();
                List<FieldMeta> fieldMetas = new ArrayList<>();

                table.setTableName(tablesSet.getString("table_name"));
                preparedStatement.setString(1, table.getTableName());

                fieldsSet = preparedStatement.executeQuery();

                while(fieldsSet.next()){
                    FieldMeta fieldMeta = new FieldMeta();
                    fieldMeta.setFieldName(fieldsSet.getString("column_name"));
                    fieldMeta.setFieldType(fieldsSet.getString("data_type"));
                    fieldMetas.add(fieldMeta);
                }

                table.setTableFields(fieldMetas);
                tablesList.add(table);
            }
            statement.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tablesList;
    }


    public boolean executeQueryFromResources(String resourceName){
        String query;
        Statement statement;
        StringBuilder builder = new StringBuilder();
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName);
        BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(stream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        query = builder.toString();

        try {
            statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            return true;
        } catch (SQLException exc){
            exc.printStackTrace();
            return false;
        }
    }
}
