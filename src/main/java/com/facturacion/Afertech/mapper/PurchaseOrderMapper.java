package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import com.facturacion.Afertech.model.PurchaseOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    PurchaseOrder toEntity(PurchaseOrderRequest request);

    PurchaseOrderResponse toResponse(PurchaseOrder purchaseOrder);
}

