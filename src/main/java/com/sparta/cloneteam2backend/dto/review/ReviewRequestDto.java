package com.sparta.cloneteam2backend.dto.review;

import com.sparta.cloneteam2backend.model.Post;
import com.sparta.cloneteam2backend.model.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewRequestDto {
	private String reviewContent;
	private int reviewStar;

	public Review toReview(Post post) {
		return Review.builder()
				.post(post)
//				.user(user)
				.reviewContent(reviewContent)
				.reviewStar(reviewStar)
				.build();
	}
}