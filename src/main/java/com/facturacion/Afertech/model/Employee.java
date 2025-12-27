package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "document_number")
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // DNI / legajo
    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDate hireDate;

    // Fecha de baja (si corresponde)
    private LocalDate terminationDate;
}
