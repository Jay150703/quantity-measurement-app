package com.jay.repository;

import com.jay.entity.QuantityMeasurementEntity;
import com.jay.exception.DatabaseException;
import com.jay.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS quantity_measurements (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                operation_type VARCHAR(50),
                measurement_type VARCHAR(50),
                value1 DOUBLE,
                unit1 VARCHAR(50),
                value2 DOUBLE,
                unit2 VARCHAR(50),
                result VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO quantity_measurements
            (operation_type, measurement_type, value1, unit1, value2, unit2, result)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL_SQL = """
            SELECT operation_type, result
            FROM quantity_measurements
            ORDER BY id
            """;

    public QuantityMeasurementDatabaseRepository() {
        initializeSchema();
    }

    private void initializeSchema() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_SQL)) {
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to initialize database schema", e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
                statement.setString(1, entity.getOperation());
                statement.setString(2, null);
                statement.setObject(3, null);
                statement.setObject(4, null);
                statement.setObject(5, null);
                statement.setObject(6, null);
                statement.setString(7, entity.isError()
                        ? entity.getErrorMessage()
                        : String.valueOf(entity.getResult()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save quantity measurement", e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        List<QuantityMeasurementEntity> results = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
                 ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    String operation = rs.getString("operation_type");
                    String resultText = rs.getString("result");

                    try {
                        double result = Double.parseDouble(resultText);
                        results.add(new QuantityMeasurementEntity(operation, result));
                    } catch (Exception ex) {
                        results.add(new QuantityMeasurementEntity(operation, resultText));
                    }
                }
            }
            return results;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch quantity measurements", e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
    }
}