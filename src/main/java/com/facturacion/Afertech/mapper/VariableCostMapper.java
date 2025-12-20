package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.model.VariableCost;

public class VariableCostMapper {

    public static VariableCostResponse toResponse(VariableCost cost) {
        VariableCostResponse response = new VariableCostResponse();
        response.setId(cost.getId());
        response.setDescription(cost.getDescription());
        response.setAmount(cost.getAmount());
        response.setDate(cost.getDate());
        response.setBusinessName(cost.getBusinessName());

        if (cost.getCostType() != null) {
            response.setCostTypeId(cost.getCostType().getId());
            response.setCostTypeName(cost.getCostType().getName());
        }

        if (cost.getProject() != null) {
            response.setProjectId(cost.getProject().getId());
            response.setProjectName(cost.getProject().getName());
        }

        return response;
    }
}
