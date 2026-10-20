package com.example.intlhub.dto;

import com.example.intlhub.entity.User;

import java.time.LocalDateTime;

// AI-assisted: what the API returns about a user. It never contains the password hash.
public record UserResponse(
        Long id,
        String username,
        String email,
        String nationality,
        String city,
        String role,
        Boolean isSenior,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getNationality(),
                user.getCity(),
                user.getRole(),
                user.getIsSenior(),
                user.getCreatedAt());
    }
}
