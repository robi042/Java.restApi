package com.test.restapi.controller;

import com.test.restapi.dto.ApiResponse;
import com.test.restapi.entity.User;
import com.test.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class userManagement {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/authenticate")
    public ApiResponse<?> authenticateUser(@RequestBody User user) {
        return userService.authenticateUser(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('admin')")
    public ApiResponse<?> getMyUser(Authentication authentication) {
        return userService.getMyInfo(authentication);
    }
}
