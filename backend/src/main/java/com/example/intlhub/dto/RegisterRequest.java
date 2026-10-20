package com.example.intlhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// AI-assisted: input for POST /api/auth/register, validated before it reaches the service
public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 50)
        @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "may contain letters, digits and underscore only")
        String username,

        @NotBlank @Email @Size(max = 100)
        String email,

        // BCrypt only uses the first 72 bytes, so longer passwords are rejected
        @NotBlank @Size(min = 8, max = 72)
        String password,

        @Size(max = 50) String nationality,
        @Size(max = 50) String city
) {}
