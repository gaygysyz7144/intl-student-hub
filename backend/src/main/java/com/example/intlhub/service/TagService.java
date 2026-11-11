package com.example.intlhub.service;

import com.example.intlhub.dto.TagResponse;
import com.example.intlhub.entity.Post;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.PostMapper;
import com.example.intlhub.mapper.TagMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// AI-assisted: business rules for tags (replace a whole set in one transaction, post tags need the owner or an admin)
@Service
public class TagService {

    private final TagMapper tagMapper;
    private final PostMapper postMapper;

    public TagService(TagMapper tagMapper, PostMapper postMapper) {
        this.tagMapper = tagMapper;
        this.postMapper = postMapper;
    }

    public List<TagResponse> all() {
        return tagMapper.findAll().stream().map(TagResponse::from).toList();
    }

    public List<TagResponse> userTags(Long userId) {
        return tagMapper.findByUser(userId).stream().map(TagResponse::from).toList();
    }

    // Delete and insert run in one transaction: if an insert fails, the old tags are kept.
    @Transactional
    public List<TagResponse> setUserTags(Long userId, List<Long> tagIds) {
        List<Long> ids = validIds(tagIds);
        tagMapper.clearUserTags(userId);
        for (Long tagId : ids) {
            tagMapper.addUserTag(userId, tagId);
        }
        return userTags(userId);
    }

    public List<TagResponse> postTags(Long postId) {
        Post post = postMapper.findById(postId);
        if (post == null || !"ACTIVE".equals(post.getStatus())) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return tagMapper.findByPost(postId).stream().map(TagResponse::from).toList();
    }

    @Transactional
    public List<TagResponse> setPostTags(Long postId, Long userId, String role, List<Long> tagIds) {
        Post post = postMapper.findById(postId);
        if (post == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Post not found");
        }
        boolean isOwner = post.getUserId().equals(userId);
        boolean isAdmin = "ADMIN".equals(role);
        if (!isOwner && !isAdmin) {
            throw new ApiException(HttpStatus.FORBIDDEN, "You can only change your own posts");
        }
        List<Long> ids = validIds(tagIds);
        tagMapper.clearPostTags(postId);
        for (Long tagId : ids) {
            tagMapper.addPostTag(postId, tagId);
        }
        return tagMapper.findByPost(postId).stream().map(TagResponse::from).toList();
    }

    // The same id twice counts once; every id must exist.
    private List<Long> validIds(List<Long> tagIds) {
        List<Long> ids = tagIds.stream().distinct().toList();
        if (!ids.isEmpty() && tagMapper.countByIds(ids) != ids.size()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unknown tag id");
        }
        return ids;
    }
}