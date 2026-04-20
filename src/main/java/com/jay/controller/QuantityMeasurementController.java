package com.jay.controller;

import com.jay.dto.QuantityDTO;
import com.jay.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performComparison(QuantityDTO a, QuantityDTO b) {
        System.out.println("Comparison: " + service.compare(a, b));
    }

    public void performConversion(QuantityDTO input, String target) {
        System.out.println("Conversion: " + service.convert(input, target).getValue());
    }

    public void performAddition(QuantityDTO a, QuantityDTO b, String target) {
        System.out.println("Addition: " + service.add(a, b, target).getValue());
    }
}