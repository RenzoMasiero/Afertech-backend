package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.AuthRequest;
import com.facturacion.Afertech.dto.AuthResponse;
import com.facturacion.Afertech.model.User;
import com.facturacion.Afertech.security.JwtService;
import com.facturacion.Afertech.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request
    ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetailsImpl principal =
                (UserDetailsImpl) authentication.getPrincipal();

        User user = principal.getUser();

        String token = jwtService.generateToken(principal);

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setRole(user.getRole().name());

        response.setUser(userInfo);

        return ResponseEntity.ok(response);
    }
}
