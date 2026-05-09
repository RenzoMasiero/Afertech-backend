package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ExchangeRateMapper {

    ExchangeRateResponse toResponse(ExchangeRate exchangeRate);
}
