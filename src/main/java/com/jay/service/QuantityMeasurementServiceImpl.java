package com.jay.service;

import com.jay.*;
import com.jay.dto.QuantityDTO;
import com.jay.entity.QuantityMeasurementEntity;
import com.jay.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(String type, String unit) {
        return switch (type.toLowerCase()) {
            case "length" -> LengthUnit.valueOf(unit);
            case "weight" -> WeightUnit.valueOf(unit);
            case "volume" -> VolumeUnit.valueOf(unit);
            case "temperature" -> TemperatureUnit.valueOf(unit);
            default -> throw new IllegalArgumentException("Invalid type");
        };
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        Quantity<IMeasurable> a =
                new Quantity<>(q1.getValue(), resolveUnit(q1.getMeasurementType(), q1.getUnit()));

        Quantity<IMeasurable> b =
                new Quantity<>(q2.getValue(), resolveUnit(q2.getMeasurementType(), q2.getUnit()));

        boolean result = a.equals(b);

        repository.save(new QuantityMeasurementEntity("COMPARE", result ? 1 : 0));

        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {

        IMeasurable source = resolveUnit(input.getMeasurementType(), input.getUnit());
        IMeasurable target = resolveUnit(input.getMeasurementType(), targetUnit);

        Quantity<IMeasurable> q = new Quantity<>(input.getValue(), source);
        Quantity<IMeasurable> converted = q.convertTo(target);

        repository.save(new QuantityMeasurementEntity("CONVERT", converted.getValue()));

        return new QuantityDTO(converted.getValue(), targetUnit, input.getMeasurementType());
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        IMeasurable u1 = resolveUnit(q1.getMeasurementType(), q1.getUnit());
        IMeasurable u2 = resolveUnit(q2.getMeasurementType(), q2.getUnit());
        IMeasurable target = resolveUnit(q1.getMeasurementType(), targetUnit);

        Quantity<IMeasurable> a = new Quantity<>(q1.getValue(), u1);
        Quantity<IMeasurable> b = new Quantity<>(q2.getValue(), u2);

        Quantity<IMeasurable> result = a.add(b, target);

        repository.save(new QuantityMeasurementEntity("ADD", result.getValue()));

        return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());
    }
}