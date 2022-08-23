package com.sparta.cloneteam2backend.model;

import com.sparta.cloneteam2backend.dto.review.ReviewRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
public class Review extends Timestamped{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id")
//	private User user;
	@Column
	private String reviewContent;
	@Column
	private int reviewStar;

	@Builder
	public Review(Post post, String reviewContent, int reviewStar) {
		this.post = post;
		this.reviewContent = reviewContent;
		this.reviewStar = reviewStar;
	}

	public void updateReview(ReviewRequestDto requestDto) {
		this.reviewContent = requestDto.getReviewContent();
		this.reviewStar = requestDto.getReviewStar();
	}
}