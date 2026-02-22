package com.facturacion.Afertech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AfertechApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfertechApplication.class, args);
    }
}
