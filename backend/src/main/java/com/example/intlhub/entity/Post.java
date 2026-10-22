package com.example.intlhub.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// AI-assisted: maps one row of the post table; authorName is filled by the JOIN with user
public class Post {
    private Long id;
    private Long userId;
    private String category;
    private String title;
    private String content;
    private BigDecimal price;
    private String city;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
