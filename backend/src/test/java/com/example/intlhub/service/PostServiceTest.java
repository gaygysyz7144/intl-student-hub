package com.example.intlhub.service;

import com.example.intlhub.dto.PostRequest;
import com.example.intlhub.entity.Post;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// AI-assisted: unit tests for the ownership and validation rules in PostService
class PostServiceTest {

    private PostMapper postMapper;
    private PostService service;

    @BeforeEach
    void setUp() {
        postMapper = Mockito.mock(PostMapper.class);
        service = new PostService(postMapper);
    }

    private Post postOwnedBy(Long ownerId) {
        Post post = new Post();
        post.setUserId(ownerId);
        post.setStatus("ACTIVE");
        return post;
    }

    @Test
    void deleteRejectsNonOwner() {
        Mockito.when(postMapper.findById(10L)).thenReturn(postOwnedBy(1L));

        ApiException ex = assertThrows(ApiException.class, () -> service.delete(10L, 2L, "USER"));

        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        Mockito.verify(postMapper, Mockito.never()).delete(Mockito.anyLong());
    }

    @Test
    void deleteAllowsOwner() {
        Mockito.when(postMapper.findById(10L)).thenReturn(postOwnedBy(1L));

        service.delete(10L, 1L, "USER");

        Mockito.verify(postMapper).delete(10L);
    }

    @Test
    void deleteAllowsAdmin() {
        Mockito.when(postMapper.findById(10L)).thenReturn(postOwnedBy(1L));

        service.delete(10L, 2L, "ADMIN");

        Mockito.verify(postMapper).delete(10L);
    }

    @Test
    void deleteMissingPostReturnsNotFound() {
        Mockito.when(postMapper.findById(99L)).thenReturn(null);

        ApiException ex = assertThrows(ApiException.class, () -> service.delete(99L, 1L, "USER"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void updateRejectsNonOwner() {
        Mockito.when(postMapper.findById(10L)).thenReturn(postOwnedBy(1L));
        PostRequest req = new PostRequest("HOUSING", "New title", "New content", null, null);

        ApiException ex = assertThrows(ApiException.class, () -> service.update(10L, 2L, "USER", req));

        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
        Mockito.verify(postMapper, Mockito.never()).update(Mockito.any(Post.class));
    }

    @Test
    void createRejectsInvalidCategory() {
        PostRequest req = new PostRequest("NOPE", "A title", "Some content", null, null);

        ApiException ex = assertThrows(ApiException.class, () -> service.create(1L, req));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        Mockito.verify(postMapper, Mockito.never()).insert(Mockito.any(Post.class));
    }
}