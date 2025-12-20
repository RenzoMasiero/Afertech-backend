package com.facturacion.Afertech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "variable_cost_types")
public class VariableCostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // ===== getters & setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
