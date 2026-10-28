package com.example.intlhub.service;

import com.example.intlhub.config.JwtService;
import com.example.intlhub.dto.LoginRequest;
import com.example.intlhub.dto.RegisterRequest;
import com.example.intlhub.entity.User;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// AI-assisted: unit tests for AuthService, no database needed
class AuthServiceTest {

    @Test
    void registerRejectsExistingUsername() {
        UserMapper userMapper = Mockito.mock(UserMapper.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        JwtService jwtService = Mockito.mock(JwtService.class);
        AuthService service = new AuthService(userMapper, encoder, jwtService);

        Mockito.when(userMapper.findByUsername("taken")).thenReturn(new User());

        RegisterRequest req = new RegisterRequest("taken", "a@example.com", "password123", null, null);
        ApiException ex = assertThrows(ApiException.class, () -> service.register(req));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void loginRejectsWrongPassword() {
        UserMapper userMapper = Mockito.mock(UserMapper.class);
        PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);
        JwtService jwtService = Mockito.mock(JwtService.class);
        AuthService service = new AuthService(userMapper, encoder, jwtService);

        User user = new User();
        user.setPasswordHash("stored-hash");
        Mockito.when(userMapper.findByUsername("alice")).thenReturn(user);
        Mockito.when(encoder.matches("wrong-password", "stored-hash")).thenReturn(false);

        LoginRequest req = new LoginRequest("alice", "wrong-password");
        ApiException ex = assertThrows(ApiException.class, () -> service.login(req));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }
}