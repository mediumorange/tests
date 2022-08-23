package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();
}