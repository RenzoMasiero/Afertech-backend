package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientResponse {

    private Long id;
    private String name;
    private String taxId;
    private boolean active;

    // Fecha de carga funcional
    private LocalDateTime loadedAt;

    // Usuario que cargó (dato de uso)
    private String loadedBy;
}
