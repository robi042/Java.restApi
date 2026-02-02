package com.test.restapi.service;

import com.test.restapi.dto.ApiResponse;
import com.test.restapi.dto.request.LoginRequest;
import com.test.restapi.dto.request.RegisterRequest;
import com.test.restapi.dto.response.AuthResponse;
import com.test.restapi.dto.response.UserResponse;
import com.test.restapi.entity.User;
import com.test.restapi.exception.AppException;
import com.test.restapi.exception.ErrorCode;
import com.test.restapi.repository.UserRepository;
import com.test.restapi.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ApiResponse<?> register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "user");

        try {
            repository.save(user);
            return ApiResponse.success("User registered successfully", null);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS, "Email already exists");
        } catch (Exception e) {
            throw new AppException(ErrorCode.SERVER_ERROR, "Registration failed", null);
        }
    }

    public ApiResponse<AuthResponse> authenticate(LoginRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS, "Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        AuthResponse data = new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
        return new ApiResponse<>(true, 200, "Authenticated successfully", data, null);
    }

    public ApiResponse<?> getAllUsers() {
        List<UserResponse> list = repository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .collect(Collectors.toList());
        return ApiResponse.success("Users fetched successfully", list);
    }

    public ApiResponse<UserResponse> getMyInfo(Authentication authentication) {
        Claims claims = (Claims) authentication.getDetails();
        Long id = claims.get("id", Long.class);

        User user = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));

        UserResponse data = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
        return ApiResponse.success("User info fetched", data);
    }
}
