package com.test.restapi.service;

import com.test.restapi.dto.ApiResponse;
import com.test.restapi.entity.User;
import com.test.restapi.exception.AppException;
import com.test.restapi.repository.userRepository;
import com.test.restapi.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private userRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ApiResponse<?> registerUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            return new ApiResponse<>(true, 200, "User registered successfully", null, null);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(400, "Email already exists!");
        } catch (Exception e) {
            throw new AppException(500, "Something went wrong", e.getMessage());
        }
    }

    public ApiResponse<?> authenticateUser(User user) {
        User u = repository.findByEmail(user.getEmail())
                .orElseThrow(() -> new AppException(404, "User not found"));

        if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            throw new AppException(401, "Invalid credentials");
        }

        String token = jwtUtil.generateToken(u);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("id", u.getId());
        response.put("name", u.getName());
        response.put("email", u.getEmail());
        response.put("role", u.getRole());

        return new ApiResponse<>(true, 200, "User authenticated successfully", response, null);
    }

    public ApiResponse<?> getAllUsers() {
        List<User> users = repository.findAll();

        List<Map<String, Object>> userList = users.stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("name", u.getName());
            map.put("role", u.getRole());
            return map;
        }).collect(Collectors.toList());

        return new ApiResponse<>(true, 200, "Users fetched successfully", userList, null);
    }

    public ApiResponse<?> getMyInfo(Authentication authentication) {
        Claims claims = (Claims) authentication.getDetails();
        Long id = claims.get("id", Long.class);

        User me = repository.findById(id)
                .orElseThrow(() -> new AppException(404, "User not found"));

        Map<String, Object> data = Map.of(
                "id", me.getId(),
                "email", me.getEmail(),
                "role", me.getRole()
        );

        return new ApiResponse<>(true, 200, "User info fetched", data, null);
    }


}
