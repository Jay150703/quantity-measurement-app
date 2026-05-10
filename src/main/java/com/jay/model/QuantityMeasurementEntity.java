package com.jay.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quantity_measurements")
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;

    private double result;

    private boolean error;

    private String errorMessage;

    public QuantityMeasurementEntity() {
    }

    public QuantityMeasurementEntity(String operation, double result) {
        this.operation = operation;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String operation, String errorMessage) {
        this.operation = operation;
        this.errorMessage = errorMessage;
        this.error = true;
    }

    public Long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public double getResult() {
        return result;
    }

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}