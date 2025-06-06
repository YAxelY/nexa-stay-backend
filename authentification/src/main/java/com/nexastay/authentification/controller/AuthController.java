package com.nexastay.authentification.controller;

import com.nexastay.authentification.dto.AuthResponse;
import com.nexastay.authentification.dto.LoginRequest;
import com.nexastay.authentification.dto.RegisterRequest;
import com.nexastay.authentification.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            log.info("Received registration request for email: {}", request.getEmail());

            // Validate request
            if (request.getNom() == null || request.getNom().trim().isEmpty()) {
                log.warn("Registration failed: Name is required");
                return ResponseEntity.badRequest().body(new ErrorResponse("Name is required"));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                log.warn("Registration failed: Email is required");
                return ResponseEntity.badRequest().body(new ErrorResponse("Email is required"));
            }
            if (request.getMotDePasse() == null || request.getMotDePasse().length() < 8) {
                log.warn("Registration failed: Password must be at least 8 characters");
                return ResponseEntity.badRequest().body(new ErrorResponse("Password must be at least 8 characters"));
            }

            AuthResponse response = authService.register(request);
            log.info("Successfully registered user with email: {}", request.getEmail());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            log.error("Registration failed with RuntimeException: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Registration failed with unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new ErrorResponse("An unexpected error occurred"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            log.info("Received login request for email: {}", request.getEmail());
            AuthResponse response = authService.login(request);
            log.info("Successfully logged in user with email: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



