package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostTypeRequest {

    @NotBlank
    private String name;
}
