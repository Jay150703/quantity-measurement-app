package com.jay.service;

import com.jay.*;
import com.jay.dto.QuantityDTO;
import com.jay.model.QuantityMeasurementEntity;
import com.jay.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.jay.dto.QuantityMeasurementDTO;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(String type, String unit) {

        return switch (type.toLowerCase()) {

            case "lengthunit" ->
                    LengthUnit.valueOf(unit.toUpperCase());

            case "weightunit" ->
                    WeightUnit.valueOf(unit.toUpperCase());

            case "volumeunit" ->
                    VolumeUnit.valueOf(unit.toUpperCase());

            case "temperatureunit" ->
                    TemperatureUnit.valueOf(unit.toUpperCase());

            default ->
                    throw new IllegalArgumentException("Invalid measurement type");
        };
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> a =
                    new Quantity<>(
                            q1.getValue(),
                            resolveUnit(
                                    q1.getMeasurementType(),
                                    q1.getUnit()
                            )
                    );

            Quantity<IMeasurable> b =
                    new Quantity<>(
                            q2.getValue(),
                            resolveUnit(
                                    q2.getMeasurementType(),
                                    q2.getUnit()
                            )
                    );

            boolean result = a.equals(b);

            repository.save(
                    new QuantityMeasurementEntity(
                            "COMPARE",
                            result ? 1 : 0
                    )
            );

            return result;

        } catch (Exception ex) {

            repository.save(
                    new QuantityMeasurementEntity(
                            "COMPARE",
                            ex.getMessage()
                    )
            );

            throw ex;
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {

        try {

            IMeasurable source =
                    resolveUnit(
                            input.getMeasurementType(),
                            input.getUnit()
                    );

            IMeasurable target =
                    resolveUnit(
                            input.getMeasurementType(),
                            targetUnit
                    );

            Quantity<IMeasurable> quantity =
                    new Quantity<>(
                            input.getValue(),
                            source
                    );

            Quantity<IMeasurable> converted =
                    quantity.convertTo(target);

            repository.save(
                    new QuantityMeasurementEntity(
                            "CONVERT",
                            converted.getValue()
                    )
            );

            return new QuantityDTO(
                    converted.getValue(),
                    targetUnit,
                    input.getMeasurementType()
            );

        } catch (Exception ex) {

            repository.save(
                    new QuantityMeasurementEntity(
                            "CONVERT",
                            ex.getMessage()
                    )
            );

            throw ex;
        }
    }

    @Override
    public QuantityDTO add(
            QuantityDTO q1,
            QuantityDTO q2,
            String targetUnit
    ) {

        try {

            IMeasurable unit1 =
                    resolveUnit(
                            q1.getMeasurementType(),
                            q1.getUnit()
                    );

            IMeasurable unit2 =
                    resolveUnit(
                            q2.getMeasurementType(),
                            q2.getUnit()
                    );

            IMeasurable target =
                    resolveUnit(
                            q1.getMeasurementType(),
                            targetUnit
                    );

            Quantity<IMeasurable> a =
                    new Quantity<>(
                            q1.getValue(),
                            unit1
                    );

            Quantity<IMeasurable> b =
                    new Quantity<>(
                            q2.getValue(),
                            unit2
                    );

            Quantity<IMeasurable> result =
                    a.add(b, target);

            repository.save(
                    new QuantityMeasurementEntity(
                            "ADD",
                            result.getValue()
                    )
            );

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    q1.getMeasurementType()
            );

        } catch (Exception ex) {

            repository.save(
                    new QuantityMeasurementEntity(
                            "ADD",
                            ex.getMessage()
                    )
            );

            throw ex;
        }
    }

    @Override
    public List<QuantityMeasurementDTO>
    getOperationHistory(String operation) {

        return QuantityMeasurementDTO.fromEntityList(
                repository.findByOperation(operation)
        );
    }

    @Override
    public List<QuantityMeasurementDTO>
    getErroredHistory() {

        return QuantityMeasurementDTO.fromEntityList(
                repository.findByErrorTrue()
        );
    }

    @Override
    public long getOperationCount(String operation) {

        return repository.countByOperationAndErrorFalse(operation);
    }
}