package com.example.intlhub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

// AI-assisted: input for PUT /api/users/me/tags (the full new set of interest tags, at most 10)
public record TagIdsRequest(
        @NotNull @Size(max = 10)
        List<@NotNull Long> tagIds
) {}