package com.jay.entity;

public class QuantityMeasurementEntity {

    private final String operation;
    private final double result;
    private final boolean isError;
    private final String errorMessage;

    public QuantityMeasurementEntity(String operation, double result) {
        this.operation = operation;
        this.result = result;
        this.isError = false;
        this.errorMessage = null;
    }

    public QuantityMeasurementEntity(String operation, String errorMessage) {
        this.operation = operation;
        this.result = 0.0;
        this.isError = true;
        this.errorMessage = errorMessage;
    }

    public String getOperation() {
        return operation;
    }

    public double getResult() {
        return result;
    }

    public boolean isError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}