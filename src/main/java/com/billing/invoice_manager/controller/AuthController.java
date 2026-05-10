package com.billing.invoice_manager.controller;

import com.billing.invoice_manager.dto.request.LoginRequest;
import com.billing.invoice_manager.dto.response.LoginResponse;
import com.billing.invoice_manager.entity.User;
import com.billing.invoice_manager.security.JwtService;
import com.billing.invoice_manager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = authService.authenticate(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        LoginResponse response = new LoginResponse(
                token,
                user.getEmail(),
                user.getRole(),
                user.getFullName()
        );
        return ResponseEntity.ok(response);
    }
}