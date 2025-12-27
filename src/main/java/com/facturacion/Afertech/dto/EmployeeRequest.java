package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    // DNI / legajo
    @NotBlank
    private String documentNumber;

    @NotNull
    private LocalDate hireDate;

    private LocalDate terminationDate;

    @NotNull
    private Boolean active;
}
