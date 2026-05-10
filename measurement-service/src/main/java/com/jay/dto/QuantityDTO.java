package com.jay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    private Double value;

    @NotBlank(message = "Unit cannot be blank")
    private String unit;

    @NotBlank(message = "Measurement type cannot be blank")
    @Pattern(
            regexp = "LengthUnit|WeightUnit|VolumeUnit|TemperatureUnit",
            message = "Measurement type must be valid"
    )
    private String measurementType;

    public QuantityDTO() {
    }

    public QuantityDTO(
            Double value,
            String unit,
            String measurementType
    ) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }
}