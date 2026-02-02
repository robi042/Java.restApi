package com.test.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Public health/DB and Redis check endpoints. No auth required.
 */
@RestController
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    @GetMapping("/db-test")
    public String dbTest() {
        StringBuilder sb = new StringBuilder();
        String dbUrl = env.getProperty("spring.datasource.url");
        sb.append("Testing DB: ").append(dbUrl).append("\n");

        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) {
                sb.append("✅ Connection successful!\n");
                sb.append("Database: ").append(conn.getMetaData().getDatabaseProductName()).append("\n");
                sb.append("Version: ").append(conn.getMetaData().getDatabaseProductVersion()).append("\n");
            } else {
                sb.append("❌ Connection failed!");
            }
        } catch (SQLException e) {
            sb.append("❌ Error: ").append(e.getMessage());
        }
        return sb.toString();
    }

    @GetMapping("/redis-test")
    public String redisTest() {
        StringBuilder sb = new StringBuilder();
        String host = env.getProperty("spring.data.redis.host", "localhost");
        String port = env.getProperty("spring.data.redis.port", "6379");
        sb.append("Testing Redis: ").append(host).append(":").append(port).append("\n");

        if (redisConnectionFactory == null) {
            sb.append("❌ Redis not configured (RedisConnectionFactory not available)");
            return sb.toString();
        }
        try {
            String pong = redisConnectionFactory.getConnection().ping();
            sb.append("✅ Redis PING: ").append(pong).append("\n");
        } catch (Exception e) {
            sb.append("❌ Redis error: ").append(e.getMessage());
        }
        return sb.toString();
    }
}
