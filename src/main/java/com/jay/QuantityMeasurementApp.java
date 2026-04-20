package com.jay;

import com.jay.controller.QuantityMeasurementController;
import com.jay.dto.QuantityDTO;
import com.jay.repository.QuantityMeasurementCacheRepository;
import com.jay.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        var repo = QuantityMeasurementCacheRepository.getInstance();
        var service = new QuantityMeasurementServiceImpl(repo);
        var controller = new QuantityMeasurementController(service);

        var q1 = new QuantityDTO(1.0, "FEET", "length");
        var q2 = new QuantityDTO(12.0, "INCHES", "length");

        controller.performComparison(q1, q2);
        controller.performAddition(q1, q2, "FEET");
    }
}