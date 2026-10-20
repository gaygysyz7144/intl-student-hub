package com.example.intlhub.entity;

import java.time.LocalDateTime;

// AI-assisted: maps one row of the user table
public class User {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private String nationality;
    private String city;
    private String role;
    private Boolean isSenior;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Boolean getIsSenior() { return isSenior; }
    public void setIsSenior(Boolean isSenior) { this.isSenior = isSenior; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
