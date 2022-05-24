package ru.catheringunit.application;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private boolean executable = true;
    private final String initQuery;
    private final Statement statement;

    public DBInitializer(DBConnector db) throws SQLException {
        StringBuilder builder = new StringBuilder();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("sql/initQuery.sql");
        if(stream != null){
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(stream));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initQuery = builder.toString();
            statement = db.getConnection().createStatement();
        } else {
            initQuery = "";
            statement = null;
            System.out.println("Проблема с доступом к файлу запроса инициализации БД");
        }

    }

    public void initDB(){
        if(executable){
            try {
                statement.execute(initQuery);
                statement.close();
                executable = false;
            } catch (SQLException exc){
                executable = false;
                exc.printStackTrace();
                System.out.println("Ошибка исполнения, код ошибки: " + exc.getErrorCode());
            }
        } else {
            System.out.println("Не подлежит повторному исполнению!");
        }
    }
}
