package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequest {

    @NotBlank
    private String name;

    // CUIT
    @NotBlank
    private String taxId;

    @NotNull
    private Boolean active;
}
