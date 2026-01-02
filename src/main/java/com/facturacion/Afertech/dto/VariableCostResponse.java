package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class VariableCostResponse {

    private Long id;

    private Long costTypeId;
    private String costTypeName;

    private BigDecimal amount;
    private LocalDate allocationMonth;
    private LocalDate paymentDate;

    private String businessName;
    private String description;
    private String projectNumber;

    // Fecha de carga funcional
    private LocalDateTime loadedAt;

    // Usuario que cargó
    private String loadedBy;
}
