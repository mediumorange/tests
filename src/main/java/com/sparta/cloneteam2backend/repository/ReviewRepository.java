package com.sparta.cloneteam2backend.repository;

import com.sparta.cloneteam2backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findAllByPostPostId(Long postId);

	@Query("SELECT AVG(r.reviewStar) FROM Review r WHERE r.post.postId = :postId")
	Optional<Double> existsAllReviewStar(@Param("postId") Long postId);
}