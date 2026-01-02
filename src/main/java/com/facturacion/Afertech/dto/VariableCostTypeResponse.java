package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VariableCostTypeResponse {

    private Long id;
    private String name;

    // Fecha de carga funcional
    private LocalDateTime loadedAt;

    // Usuario que cargó
    private String loadedBy;
}
