package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.ExchangeRateRequest;
import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ExchangeRateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    ExchangeRate toEntity(ExchangeRateRequest request);

    ExchangeRateResponse toResponse(ExchangeRate exchangeRate);
}
