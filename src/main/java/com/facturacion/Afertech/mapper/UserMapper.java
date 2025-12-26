package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.UserRequest;
import com.facturacion.Afertech.dto.UserResponse;
import com.facturacion.Afertech.model.Role;
import com.facturacion.Afertech.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role", qualifiedByName = "stringToRole")
    User toEntity(UserRequest request);

    @Mapping(target = "role", source = "role", qualifiedByName = "roleToString")
    UserResponse toResponse(User user);

    @Named("stringToRole")
    default Role stringToRole(String role) {
        return Role.valueOf(role);
    }

    @Named("roleToString")
    default String roleToString(Role role) {
        return role.name();
    }
}
