package com.example.intlhub.service;

import com.example.intlhub.dto.PageResponse;
import com.example.intlhub.dto.PostRequest;
import com.example.intlhub.dto.PostResponse;
import com.example.intlhub.entity.Post;
import com.example.intlhub.entity.PostCategory;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.PostMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

// AI-assisted: business rules for posts (validation of category, ownership checks, paging)
@Service
public class PostService {

    private static final int MAX_PAGE_SIZE = 50;
    private static final int MAX_KEYWORD_LENGTH = 100;

    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public PostResponse create(Long userId, PostRequest req) {
        Post post = new Post();
        post.setUserId(userId); // always the logged-in user, never taken from the request body
        applyFields(post, req);
        postMapper.insert(post);
        return PostResponse.from(postMapper.findById(post.getId()));
    }

    public PageResponse<PostResponse> search(String category, String city, String keyword, int page, int size) {
        String categoryFilter = category == null || category.isBlank() ? null : parseCategory(category).name();
        String cityFilter = blankToNull(city);
        String keywordFilter = prepareKeyword(keyword);

        int safeSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        int safePage = Math.max(1, page);
        int offset = (safePage - 1) * safeSize;

        List<PostResponse> items = postMapper.search(categoryFilter, cityFilter, keywordFilter, safeSize, offset)
                .stream().map(PostResponse::from).toList();
        long total = postMapper.count(categoryFilter, cityFilter, keywordFilter);
        return new PageResponse<>(items, safePage, safeSize, total);
    }

    public PostResponse get(Long id) {
        Post post = postMapper.findById(id);
        if (post == null || !"ACTIVE".equals(post.getStatus())) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return PostResponse.from(post);
    }

    public PostResponse update(Long id, Long userId, String role, PostRequest req) {
        Post post = loadOwnedPost(id, userId, role);
        applyFields(post, req);
        postMapper.update(post);
        return PostResponse.from(postMapper.findById(id));
    }

    public void delete(Long id, Long userId, String role) {
        loadOwnedPost(id, userId, role);
        postMapper.delete(id);
    }

    /** Loads a post and checks that the caller is the author or an admin. */
    private Post loadOwnedPost(Long id, Long userId, String role) {
        Post post = postMapper.findById(id);
        if (post == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Post not found");
        }
        boolean isOwner = post.getUserId().equals(userId);
        boolean isAdmin = "ADMIN".equals(role);
        if (!isOwner && !isAdmin) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You can only change your own posts");
        }
        return post;
    }

    private void applyFields(Post post, PostRequest req) {
        post.setCategory(parseCategory(req.category()).name());
        post.setTitle(req.title().trim());
        post.setContent(req.content().trim());
        post.setPrice(req.price());
        post.setCity(blankToNull(req.city()));
    }

    private PostCategory parseCategory(String value) {
        try {
            return PostCategory.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Invalid category. Allowed: " + Arrays.toString(PostCategory.values()));
        }
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    /** Escapes the LIKE wildcards so that a user typing % or _ searches for the literal character. */
    private String prepareKeyword(String keyword) {
        String cleaned = blankToNull(keyword);
        if (cleaned == null) {
            return null;
        }
        if (cleaned.length() > MAX_KEYWORD_LENGTH) {
            cleaned = cleaned.substring(0, MAX_KEYWORD_LENGTH);
        }
        return cleaned.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
