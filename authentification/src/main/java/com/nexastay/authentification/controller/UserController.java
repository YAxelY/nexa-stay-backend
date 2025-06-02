package com.nexastay.authentification.controller;

import com.nexastay.authentification.model.Role;
import com.nexastay.authentification.model.User;
import com.nexastay.authentification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userRepository.findByRole(role);
        // Remove sensitive information before sending
        users.forEach(user -> user.setMotDePasse(null));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Remove sensitive information before sending
        users.forEach(user -> user.setMotDePasse(null));
        return ResponseEntity.ok(users);
    }
}