package com.jay.controller;

import com.jay.dto.QuantityDTO;
import com.jay.dto.QuantityInputDTO;
import com.jay.dto.QuantityMeasurementDTO;
import com.jay.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/quantities")

@Tag(
        name = "Quantity Measurement Controller",
        description = "APIs for quantity measurement operations"
)
@SecurityRequirement(name = "bearerAuth")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @Operation(
            summary = "Compare two quantities",
            description = "Compares whether two quantities are equal"
    )
    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(@Valid @RequestBody QuantityInputDTO input) {
        QuantityDTO left = input.getThisQuantityDTO();
        QuantityDTO right = input.getThatQuantityDTO();

        boolean result = service.compare(left, right);

        QuantityMeasurementDTO response = new QuantityMeasurementDTO(
                null,
                "COMPARE",
                result ? 1.0 : 0.0,
                false,
                null
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Compare two quantities",
            description = "Compares whether two quantities are equal"
    )
    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convert(@RequestBody QuantityInputDTO input) {
        QuantityDTO source = input.getThisQuantityDTO();
        QuantityDTO targetInfo = input.getThatQuantityDTO();

        QuantityDTO converted = service.convert(source, targetInfo.getUnit());

        QuantityMeasurementDTO response = new QuantityMeasurementDTO(
                null,
                "CONVERT",
                converted.getValue(),
                false,
                null
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Compare two quantities",
            description = "Compares whether two quantities are equal"
    )
    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(@RequestBody QuantityInputDTO input) {
        QuantityDTO left = input.getThisQuantityDTO();
        QuantityDTO right = input.getThatQuantityDTO();

        QuantityDTO result = service.add(left, right, left.getUnit());

        QuantityMeasurementDTO response = new QuantityMeasurementDTO(
                null,
                "ADD",
                result.getValue(),
                false,
                null
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Compare two quantities",
            description = "Compares whether two quantities are equal"
    )
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementDTO>>
    getOperationHistory(
            @PathVariable String operation
    ) {

        return ResponseEntity.ok(
                service.getOperationHistory(operation)
        );
    }

    @Operation(
            summary = "Compare two quantities",
            description = "Compares whether two quantities are equal"
    )
    @GetMapping("/history/errored")
    public ResponseEntity<List<QuantityMeasurementDTO>>
    getErroredHistory() {

        return ResponseEntity.ok(
                service.getErroredHistory()
        );
    }

    @Operation(
            summary = "Compare two quantities",
            description = "Compares whether two quantities are equal"
    )
    @GetMapping("/count/{operation}")
    public ResponseEntity<Long>
    getOperationCount(
            @PathVariable String operation
    ) {

        return ResponseEntity.ok(
                service.getOperationCount(operation)
        );
    }
}