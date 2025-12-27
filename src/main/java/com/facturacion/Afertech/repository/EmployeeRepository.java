package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByDocumentNumber(String documentNumber);
}
