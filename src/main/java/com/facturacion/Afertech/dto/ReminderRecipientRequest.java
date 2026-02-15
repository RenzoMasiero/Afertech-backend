package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReminderRecipientRequest {

    @NotBlank
    @Email
    private String email;

    private Boolean active;
}
