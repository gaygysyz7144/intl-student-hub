package com.example.intlhub.controller;

import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.dto.PostRequest;
import com.example.intlhub.dto.PostResponse;
import com.example.intlhub.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// AI-assisted: GET is public (read and search), POST/PUT/DELETE need a valid token
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostRequest request,
                                               @RequestAttribute("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(userId, request));
    }

    @GetMapping
    public PageResponse<PostResponse> search(@RequestParam(required = false) String category,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return postService.search(category, city, keyword, page, size);
    }

    @GetMapping("/{id}")
    public PostResponse get(@PathVariable Long id) {
        return postService.get(id);
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id,
                               @Valid @RequestBody PostRequest request,
                               @RequestAttribute("userId") Long userId,
                               @RequestAttribute("role") String role) {
        return postService.update(id, userId, role, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestAttribute("userId") Long userId,
                                       @RequestAttribute("role") String role) {
        postService.delete(id, userId, role);
        return ResponseEntity.noContent().build();
    }
}
