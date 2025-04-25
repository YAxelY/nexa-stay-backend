package com.nexa.authentification.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    
    @GetMapping("/protected")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public String protectedEndpoint() {
        return "Accès autorisé !";
    }
}