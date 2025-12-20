package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
