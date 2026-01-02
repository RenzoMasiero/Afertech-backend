package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;

    // Fecha de carga funcional
    private LocalDateTime loadedAt;

    // Usuario que cargó
    private String loadedBy;
}
