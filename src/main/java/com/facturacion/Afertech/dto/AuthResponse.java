package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String token;
    private UserInfo user;

    @Getter
    @Setter
    public static class UserInfo {
        private Long id;
        private String email;
        private String role;
    }
}
