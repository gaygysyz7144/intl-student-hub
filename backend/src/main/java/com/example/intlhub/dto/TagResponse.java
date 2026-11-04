package com.example.intlhub.dto;

import com.example.intlhub.entity.Tag;

// AI-assisted: what the API returns about a tag
public record TagResponse(Long id, String name) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}