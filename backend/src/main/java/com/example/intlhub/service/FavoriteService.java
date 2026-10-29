package com.example.intlhub.service;

import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.dto.PostResponse;
import com.example.intlhub.entity.Post;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.FavoriteMapper;
import com.example.intlhub.mapper.PostMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

// AI-assisted: business rules for favorites (idempotent add and remove, paging)
@Service
public class FavoriteService {

    private static final int MAX_PAGE_SIZE = 50;

    private final FavoriteMapper favoriteMapper;
    private final PostMapper postMapper;

    public FavoriteService(FavoriteMapper favoriteMapper, PostMapper postMapper) {
        this.favoriteMapper = favoriteMapper;
        this.postMapper = postMapper;
    }

    public void add(Long userId, Long postId) {
        Post post = postMapper.findById(postId);
        if (post == null || !"ACTIVE".equals(post.getStatus())) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Post not found");
        }
        favoriteMapper.add(userId, postId); // already a favorite: nothing happens, no error
    }

    public void remove(Long userId, Long postId) {
        favoriteMapper.remove(userId, postId); // not a favorite: nothing happens, no error
    }

    public PageResponse<PostResponse> list(Long userId, int page, int size) {
        int safeSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        int safePage = Math.max(1, page);
        int offset = (safePage - 1) * safeSize;

        List<PostResponse> items = favoriteMapper.findByUser(userId, safeSize, offset)
                .stream().map(PostResponse::from).toList();
        long total = favoriteMapper.countByUser(userId);
        return new PageResponse<>(items, safePage, safeSize, total);
    }
}