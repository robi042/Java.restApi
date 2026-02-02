package com.test.restapi.controller;

import com.test.restapi.dto.ApiResponse;
import com.test.restapi.dto.request.LoginRequest;
import com.test.restapi.dto.request.RegisterRequest;
import com.test.restapi.dto.response.AuthResponse;
import com.test.restapi.dto.response.UserResponse;
import com.test.restapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/authenticate")
    public ApiResponse<AuthResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return userService.authenticate(request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<UserResponse> getMe(Authentication authentication) {
        return userService.getMyInfo(authentication);
    }
}
