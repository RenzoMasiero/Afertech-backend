package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.model.FixedCost;

public class FixedCostMapper {

    public static FixedCostResponse toResponse(FixedCost fixedCost) {
        FixedCostResponse response = new FixedCostResponse();
        response.setId(fixedCost.getId());
        response.setAmount(fixedCost.getAmount());
        response.setDate(fixedCost.getDate());
        response.setDescription(fixedCost.getDescription());

        if (fixedCost.getCostType() != null) {
            response.setCostTypeId(fixedCost.getCostType().getId());
            response.setCostTypeName(fixedCost.getCostType().getName());
        }

        return response;
    }
}
