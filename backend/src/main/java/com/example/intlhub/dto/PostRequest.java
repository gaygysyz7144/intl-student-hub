package com.example.intlhub.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

// AI-assisted: input for creating or updating a post. The category value is checked in PostService.
public record PostRequest(
        @NotBlank @Size(max = 20) String category,
        @NotBlank @Size(min = 3, max = 100) String title,
        @NotBlank @Size(max = 5000) String content,
        @DecimalMin("0.00") @Digits(integer = 8, fraction = 2) BigDecimal price,
        @Size(max = 50) String city
) {}
