package com.example.intlhub.service;

import com.example.intlhub.dto.TagResponse;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.TagMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// AI-assisted: business rules for tags (replace the whole set of interest tags in one transaction)
@Service
public class TagService {

    private final TagMapper tagMapper;

    public TagService(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
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
        List<Long> ids = tagIds.stream().distinct().toList(); // the same id twice counts once
        if (!ids.isEmpty() && tagMapper.countByIds(ids) != ids.size()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Unknown tag id");
        }
        tagMapper.clearUserTags(userId);
        for (Long tagId : ids) {
            tagMapper.addUserTag(userId, tagId);
        }
        return userTags(userId);
    }
}