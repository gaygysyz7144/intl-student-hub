package com.example.intlhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// AI-assisted: input for POST /api/auth/login
public record LoginRequest(
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Size(max = 72) String password
) {}
