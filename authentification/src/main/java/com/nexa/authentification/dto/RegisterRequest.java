package com.nexa.authentification.dto;

import com.nexa.authentification.model.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String nom;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    @NotBlank
    private String motDePasse;

    @NotNull
    private Role role;
}