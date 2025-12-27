package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.EmployeeRequest;
import com.facturacion.Afertech.dto.EmployeeResponse;
import com.facturacion.Afertech.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);
}
