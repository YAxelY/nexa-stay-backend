package com.nexastay.authentification.service;

import com.nexastay.authentification.dto.AuthResponse;
import com.nexastay.authentification.dto.LoginRequest;
import com.nexastay.authentification.dto.RegisterRequest;
import com.nexastay.authentification.model.Role;
import com.nexastay.authentification.model.User;
import com.nexastay.authentification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        log.info("Starting registration process for email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Email already in use: {}", request.getEmail());
            throw new RuntimeException("Email already in use");
        }

        // Set role to CLIENT by default
        User user = User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .role(Role.CLIENT) // Default role
                .build();

        log.debug("Saving new user to database");
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        log.info("Registration completed successfully for user: {}", user.getEmail());

        return AuthResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .email(user.getEmail())
                .name(user.getNom())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Processing login request for email: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error("User not found during login: {}", request.getEmail());
                    return new RuntimeException("User not found");
                });

        String jwtToken = jwtService.generateToken(user);
        log.info("Login successful for user: {}", user.getEmail());

        return AuthResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .email(user.getEmail())
                .name(user.getNom())
                .build();
    }
}