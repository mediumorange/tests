package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserUsername(String userUsername);
    Optional<User> findByUserUsername(String userUsername);
}
