package com.jay.service;

import com.jay.dto.QuantityDTO;
import java.util.List;
import com.jay.dto.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO input, String targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    List<QuantityMeasurementDTO>
    getOperationHistory(String operation);

    List<QuantityMeasurementDTO>
    getErroredHistory();

    long getOperationCount(String operation);
}