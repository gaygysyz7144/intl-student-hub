package com.example.intlhub.service;

import com.example.intlhub.dto.MessageRequest;
import com.example.intlhub.dto.MessageResponse;
import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.entity.Message;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.MessageMapper;
import com.example.intlhub.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

// AI-assisted: business rules for private messages (no self messages, receiver must exist, inbox only)
@Service
public class MessageService {

    private static final int MAX_PAGE_SIZE = 50;

    private final MessageMapper messageMapper;
    private final UserMapper userMapper;

    public MessageService(MessageMapper messageMapper, UserMapper userMapper) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    public MessageResponse send(Long senderId, MessageRequest req) {
        if (senderId.equals(req.receiverId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "You cannot send a message to yourself");
        }
        if (userMapper.findById(req.receiverId()) == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Receiver not found");
        }
        Message message = new Message();
        message.setSenderId(senderId); // always the logged-in user, never taken from the request body
        message.setReceiverId(req.receiverId());
        message.setContent(req.content().trim());
        messageMapper.insert(message);
        return MessageResponse.from(messageMapper.findById(message.getId()));
    }

    public PageResponse<MessageResponse> inbox(Long userId, int page, int size) {
        int safeSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        int safePage = Math.max(1, page);
        int offset = (safePage - 1) * safeSize;

        List<MessageResponse> items = messageMapper.findInbox(userId, safeSize, offset)
                .stream().map(MessageResponse::from).toList();
        long total = messageMapper.countInbox(userId);
        return new PageResponse<>(items, safePage, safeSize, total);
    }
}