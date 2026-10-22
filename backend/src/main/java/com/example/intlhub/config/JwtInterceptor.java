package com.example.intlhub.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

// AI-assisted: runs before protected controllers. Valid token -> request continues with userId and role attached.
// Reading posts (GET /api/posts...) is public so that visitors can browse without an account.
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; // CORS preflight carries no token
        }
        if (isPublicRead(request)) {
            return true;
        }
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            reject(response);
            return false;
        }
        try {
            Claims claims = jwtService.parse(header.substring(7));
            request.setAttribute("userId", Long.valueOf(claims.getSubject()));
            request.setAttribute("role", claims.get("role", String.class));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            reject(response);
            return false;
        }
    }

    private boolean isPublicRead(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "GET".equalsIgnoreCase(request.getMethod())
                && (path.equals("/api/posts") || path.startsWith("/api/posts/"));
    }

    private void reject(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\":\"Unauthorized\"}");
    }
}
