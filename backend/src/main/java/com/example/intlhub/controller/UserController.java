package com.example.intlhub.controller;

import com.example.intlhub.dto.UserResponse;
import com.example.intlhub.entity.User;
import com.example.intlhub.exception.ApiException;
import com.example.intlhub.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// AI-assisted: protected endpoint, proves that the token check works
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public UserResponse me(@RequestAttribute("userId") Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found");
        }
        return UserResponse.from(user);
    }
}
