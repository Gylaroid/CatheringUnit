package ru.catheringunit.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.catheringunit.entity.FieldMeta;
import ru.catheringunit.entity.TableMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class DBConnector {

    @Value("${database.url}")
    private String URL;

    @Value("${database.user}")
    private String USER;

    @Value("${database.password}")
    private String PASSWORD;

    private Connection connection;

    public DBConnector(){
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            System.out.println(USER + " " + PASSWORD + " " + URL);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
