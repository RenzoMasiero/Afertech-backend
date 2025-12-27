package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.SupplierRequest;
import com.facturacion.Afertech.dto.SupplierResponse;
import com.facturacion.Afertech.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toEntity(SupplierRequest request);

    SupplierResponse toResponse(Supplier supplier);
}
