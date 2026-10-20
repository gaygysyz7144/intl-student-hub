package com.example.intlhub.service;

import com.example.intlhub.config.JwtService;
import com.example.intlhub.dto.AuthResponse;
import com.example.intlhub.dto.LoginRequest;
import com.example.intlhub.dto.RegisterRequest;
import com.example.intlhub.dto.UserResponse;
import com.example.intlhub.entity.User;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// AI-assisted: registration and login logic
@Service
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest req) {
        if (userMapper.findByUsername(req.username()) != null) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already taken");
        }
        if (userMapper.findByEmail(req.email()) != null) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already registered");
        }
        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password())); // never store the plain password
        user.setNationality(req.nationality());
        user.setCity(req.city());
        userMapper.insert(user);
        // reload so that database defaults (role, created_at) are included
        return UserResponse.from(userMapper.findById(user.getId()));
    }

    public AuthResponse login(LoginRequest req) {
        User user = userMapper.findByUsername(req.username());
        // same message for unknown user and wrong password, so attackers cannot tell which one failed
        if (user == null || !passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        String token = jwtService.createToken(user.getId(), user.getUsername(), user.getRole());
        return new AuthResponse(token, UserResponse.from(user));
    }
}
