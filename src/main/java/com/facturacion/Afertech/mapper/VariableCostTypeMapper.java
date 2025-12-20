package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import com.facturacion.Afertech.model.VariableCostType;

public class VariableCostTypeMapper {

    public static VariableCostType toEntity(VariableCostTypeRequest request) {
        VariableCostType type = new VariableCostType();
        type.setName(request.getName());
        return type;
    }

    public static VariableCostTypeResponse toResponse(VariableCostType type) {
        VariableCostTypeResponse response = new VariableCostTypeResponse();
        response.setId(type.getId());
        response.setName(type.getName());
        return response;
    }
}
