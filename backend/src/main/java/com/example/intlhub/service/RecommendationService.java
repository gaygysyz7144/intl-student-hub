package com.example.intlhub.service;

import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.dto.PostResponse;
import com.example.intlhub.mapper.RecommendationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

// AI-assisted: rule-based recommendations (shared tags), paging like the other lists
@Service
public class RecommendationService {

    private static final int MAX_PAGE_SIZE = 50;

    private final RecommendationMapper recommendationMapper;

    public RecommendationService(RecommendationMapper recommendationMapper) {
        this.recommendationMapper = recommendationMapper;
    }

    public PageResponse<PostResponse> recommend(Long userId, int page, int size) {
        int safeSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        int safePage = Math.max(1, page);
        int offset = (safePage - 1) * safeSize;

        List<PostResponse> items = recommendationMapper.recommend(userId, safeSize, offset)
                .stream().map(PostResponse::from).toList();
        long total = recommendationMapper.count(userId);
        return new PageResponse<>(items, safePage, safeSize, total);
    }
}