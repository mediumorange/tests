package com.sparta.cloneteam2backend.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.cloneteam2backend.model.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	private Long postId;
	private Long reviewId;
	private String userName;
	private String review;

	@Builder
	public ReviewResponseDto(Review review) {
		this.createdAt = review.getCreatedAt();
		this.postId = review.getPost().getPostId();
		this.reviewId = review.getReviewId();
//		this.userUsername = review.getUser().getUserUsername();
		this.review = review.getReviewContent();
	}
}