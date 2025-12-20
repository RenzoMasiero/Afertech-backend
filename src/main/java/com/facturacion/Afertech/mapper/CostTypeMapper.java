package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;
import com.facturacion.Afertech.model.CostType;

public class CostTypeMapper {

    public static CostType toEntity(CostTypeRequest request) {
        CostType type = new CostType();
        type.setName(request.getName());
        return type;
    }

    public static CostTypeResponse toResponse(CostType type) {
        CostTypeResponse response = new CostTypeResponse();
        response.setId(type.getId());
        response.setName(type.getName());
        return response;
    }
}
