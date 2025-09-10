package com.test.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class dbConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @GetMapping("/db-test")
    public String testDbConnection() {
        StringBuilder sb = new StringBuilder();

        // Show current DB URL from environment
        String dbUrl = env.getProperty("spring.datasource.url");
        sb.append("Testing DB: ").append(dbUrl).append("\n");

        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) { // timeout 2 sec
                sb.append("✅ Connection successful!\n");
                sb.append("Database Product Name: ").append(conn.getMetaData().getDatabaseProductName()).append("\n");
                sb.append("Database Product Version: ").append(conn.getMetaData().getDatabaseProductVersion()).append("\n");
            } else {
                sb.append("❌ Connection failed!");
            }
        } catch (SQLException e) {
            sb.append("❌ Connection error: ").append(e.getMessage());
        }

        return sb.toString();
    }
}

