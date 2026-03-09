package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.ReminderRecipientRequest;
import com.facturacion.Afertech.dto.ReminderRecipientResponse;
import com.facturacion.Afertech.model.ReminderRecipient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReminderRecipientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "loadedAt", ignore = true)
    @Mapping(target = "loadedBy", ignore = true)
    ReminderRecipient toEntity(ReminderRecipientRequest request);

    ReminderRecipientResponse toResponse(ReminderRecipient entity);
}
