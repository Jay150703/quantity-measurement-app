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
    );