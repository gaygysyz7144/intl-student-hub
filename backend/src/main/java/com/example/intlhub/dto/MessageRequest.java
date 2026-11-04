package com.example.intlhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// AI-assisted: input for POST /api/messages. The sender is never part of the input (it comes from the token).
public record MessageRequest(
        @NotNull Long receiverId,

        @NotBlank @Size(max = 1000)
        String content
) {}