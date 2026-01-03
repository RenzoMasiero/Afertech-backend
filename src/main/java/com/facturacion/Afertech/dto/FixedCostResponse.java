package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FixedCostResponse {

    private Long id;

    private Long costTypeId;
    private String costTypeName;

    private Long employeeId;
    private String employeeName;

    private BigDecimal amount;
    private LocalDate allocationMonth;
    private LocalDate paymentDate;
    private String description;

    private LocalDateTime loadedAt;
    private String loadedBy;
}
