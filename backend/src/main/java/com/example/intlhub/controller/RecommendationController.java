package com.example.intlhub.controller;

import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.dto.PostResponse;
import com.example.intlhub.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// AI-assisted: needs a valid token; the recommendations are for the user in the token
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public PageResponse<PostResponse> recommend(@RequestAttribute("userId") Long userId,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        return recommendationService.recommend(userId, page, size);
    }
}