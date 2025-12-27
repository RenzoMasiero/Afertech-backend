package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByTaxId(String taxId);
}
