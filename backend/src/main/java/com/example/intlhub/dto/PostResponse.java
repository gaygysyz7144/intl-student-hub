package com.example.intlhub.dto;

import com.example.intlhub.entity.Post;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// AI-assisted: what the API returns about a post (author name instead of the whole user)
public record PostResponse(
        Long id,
        Long authorId,
        String authorName,
        String category,
        String title,
        String content,
        BigDecimal price,
        String city,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getUserId(),
                post.getAuthorName(),
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getPrice(),
                post.getCity(),
                post.getStatus(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
