package com.jay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;

public final class ConnectionPool {

    private static final int POOL_SIZE = ApplicationConfig.getInt("db.pool.size", 5);
    private static final Queue<Connection> POOL = new ArrayDeque<>();

    static {
        try {
            String driver = ApplicationConfig.get("db.driver");
            if (driver != null && !driver.isBlank()) {
                Class.forName(driver);
            }

            for (int i = 0; i < POOL_SIZE; i++) {
                POOL.add(createConnection());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize connection pool", e);
        }
    }

    private ConnectionPool() {
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                ApplicationConfig.get("db.url"),
                ApplicationConfig.get("db.username"),
                ApplicationConfig.get("db.password")
        );
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection = POOL.poll();
        if (connection == null || connection.isClosed()) {
            return createConnection();
        }
        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            POOL.offer(connection);
        }
    }

    public static synchronized int availableConnections() {
        return POOL.size();
    }
}