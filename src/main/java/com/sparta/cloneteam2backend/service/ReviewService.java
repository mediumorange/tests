package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.review.ReviewRequestDto;
import com.sparta.cloneteam2backend.dto.review.ReviewResponseDto;
import com.sparta.cloneteam2backend.model.Post;
import com.sparta.cloneteam2backend.model.Review;
import com.sparta.cloneteam2backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final PostService postService;

	public List<ReviewResponseDto> getReview(Long postId) {
		List<Review> reviews = reviewRepository.findAllByPostPostId(postId);
		return reviews.stream()
				.map(ReviewResponseDto::new)
				.collect(Collectors.toList());
	}

	public ReviewResponseDto createReview(Long postId, ReviewRequestDto requestDto) {
		Post post = postService.getPost(postId);
//		User user = (필요한 형식으로).getUser();
		Review review = requestDto.toReview(post);
		reviewRepository.save(review);
		return new ReviewResponseDto(review);
	}

	@Transactional
	public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto) {
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new IllegalArgumentException(""));
		review.updateReview(requestDto);
		return new ReviewResponseDto(review);
	}

	@Transactional
	public void deleteReview(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}
}