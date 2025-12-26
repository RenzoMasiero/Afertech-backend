package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.UserRequest;
import com.facturacion.Afertech.dto.UserResponse;
import com.facturacion.Afertech.mapper.UserMapper;
import com.facturacion.Afertech.model.User;
import com.facturacion.Afertech.repository.UserRepository;
import com.facturacion.Afertech.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository repository,
            UserMapper mapper,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return mapper.toResponse(repository.save(user));
    }

    @Override
    public void deactivate(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        repository.save(user);
    }
}
