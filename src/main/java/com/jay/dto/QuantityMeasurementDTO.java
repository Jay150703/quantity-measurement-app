package com.jay.dto;

import com.jay.model.QuantityMeasurementEntity;

import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementDTO {

    private Long id;
    private String operation;
    private double result;
    private boolean error;
    private String errorMessage;

    public QuantityMeasurementDTO() {
    }

    public QuantityMeasurementDTO(Long id, String operation, double result, boolean error, String errorMessage) {
        this.id = id;
        this.operation = operation;
        this.result = result;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        return new QuantityMeasurementDTO(
                entity.getId(),
                entity.getOperation(),
                entity.getResult(),
                entity.isError(),
                entity.getErrorMessage()
        );
    }

    public QuantityMeasurementEntity toEntity() {
        if (error) {
            return new QuantityMeasurementEntity(operation, errorMessage);
        }
        return new QuantityMeasurementEntity(operation, result);
    }

    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .collect(Collectors.toList());
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