package com.nexastay.authentification.config;

import com.nexastay.authentification.model.Role;
import com.nexastay.authentification.model.User;
import com.nexastay.authentification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create admin user if it doesn't exist
        if (userRepository.findByEmail("admin@nexastay.com").isEmpty()) {
            User adminUser = User.builder()
                    .nom("Admin")
                    .email("admin@nexastay.com")
                    .motDePasse(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(adminUser);
        }
    }
}