package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private LocalDate hireDate;
    private LocalDate terminationDate;

    private LocalDateTime loadedAt;
    private String loadedBy;
}
