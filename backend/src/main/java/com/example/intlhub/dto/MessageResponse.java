package com.example.intlhub.dto;

import com.example.intlhub.entity.Message;

import java.time.LocalDateTime;

// AI-assisted: what the API returns about a message
public record MessageResponse(
        Long id,
        Long senderId,
        String senderName,
        Long receiverId,
        String content,
        Boolean isRead,
        LocalDateTime createdAt
) {
    public static MessageResponse from(Message m) {
        return new MessageResponse(
                m.getId(),
                m.getSenderId(),
                m.getSenderName(),
                m.getReceiverId(),
                m.getContent(),
                m.getIsRead(),
                m.getCreatedAt());
    }
}