package com.example.intlhub.controller;

import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.dto.PostResponse;
import com.example.intlhub.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// AI-assisted: all favorites endpoints need a valid token (the user always comes from the token)
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> add(@PathVariable Long postId,
                                    @RequestAttribute("userId") Long userId) {
        favoriteService.add(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> remove(@PathVariable Long postId,
                                       @RequestAttribute("userId") Long userId) {
        favoriteService.remove(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public PageResponse<PostResponse> list(@RequestAttribute("userId") Long userId,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return favoriteService.list(userId, page, size);
    }
}