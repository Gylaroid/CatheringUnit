package ru.catheringunit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.catheringunit.application.DBInitializer;
import ru.catheringunit.application.DBConnector;

import java.sql.SQLException;

@Controller
public class InitController {
    @GetMapping("/initDB")
    public void init() throws SQLException {
        DBInitializer dbInitializer = new DBInitializer(new DBConnector());
        dbInitializer.initDB();
    }
}
