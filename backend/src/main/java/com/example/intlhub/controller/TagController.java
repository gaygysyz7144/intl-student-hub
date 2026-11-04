package com.example.intlhub.controller;

import com.example.intlhub.dto.TagIdsRequest;
import com.example.intlhub.dto.TagResponse;
import com.example.intlhub.service.TagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// AI-assisted: tag endpoints need a valid token; the user always comes from the token
@RestController
@RequestMapping("/api")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<TagResponse> all() {
        return tagService.all();
    }

    @GetMapping("/users/me/tags")
    public List<TagResponse> myTags(@RequestAttribute("userId") Long userId) {
        return tagService.userTags(userId);
    }

    @PutMapping("/users/me/tags")
    public List<TagResponse> setMyTags(@Valid @RequestBody TagIdsRequest request,
                                       @RequestAttribute("userId") Long userId) {
        return tagService.setUserTags(userId, request.tagIds());
    }
}