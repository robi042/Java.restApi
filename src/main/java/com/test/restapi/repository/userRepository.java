package com.test.restapi.repository;

import com.test.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}