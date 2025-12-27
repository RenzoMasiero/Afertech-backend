package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private boolean active;
    private LocalDate hireDate;
    private LocalDate terminationDate;
}
