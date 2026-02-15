package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReminderRecipientResponse {

    private Long id;

    private String email;

    private Boolean active;

    private LocalDateTime loadedAt;
    private String loadedBy;
}
