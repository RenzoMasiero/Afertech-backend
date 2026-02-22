package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {

    private final EmailService emailService;

    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/test-email")
    public String testEmail() {

        emailService.sendEmail(
                "renzo.masiero.dev@gmail.com",
                "Prueba Afertech",
                "Si recibís este mensaje, el SMTP funciona correctamente."
        );

        return "Email enviado";
    }
}
