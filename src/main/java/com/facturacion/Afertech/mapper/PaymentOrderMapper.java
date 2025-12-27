package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.model.PaymentOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {

    PaymentOrder toEntity(PaymentOrderRequest request);

    PaymentOrderResponse toResponse(PaymentOrder paymentOrder);
}

