package com.example.intlhub.dto;

import java.util.List;

// AI-assisted: one page of results plus the total number of matches
public record PageResponse<T>(List<T> items, int page, int size, long total) {}
