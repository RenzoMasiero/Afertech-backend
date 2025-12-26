package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.UserRequest;
import com.facturacion.Afertech.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse create(UserRequest request);

    void deactivate(Long id);
}
