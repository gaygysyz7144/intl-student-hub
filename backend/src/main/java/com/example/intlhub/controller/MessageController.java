package com.example.intlhub.controller;

import com.example.intlhub.dto.MessageRequest;
import com.example.intlhub.dto.MessageResponse;
import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// AI-assisted: all message endpoints need a valid token; the user always comes from the token
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> send(@Valid @RequestBody MessageRequest request,
                                                @RequestAttribute("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.send(userId, request));
    }

    @GetMapping
    public PageResponse<MessageResponse> inbox(@RequestAttribute("userId") Long userId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return messageService.inbox(userId, page, size);
    }
}