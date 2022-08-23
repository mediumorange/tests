package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.review.ReviewRequestDto;
import com.sparta.cloneteam2backend.dto.review.ReviewResponseDto;
import com.sparta.cloneteam2backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/posts/{postId}/reviews")
@RequiredArgsConstructor
@RestController
public class ReviewController {
	private final ReviewService reviewService;

	// 리뷰 조회
	@GetMapping
	public ResponseEntity<ResponseDto> getReview(@PathVariable Long postId) {
		List<ReviewResponseDto> reviewDtos = reviewService.getReview(postId);
		return new ResponseEntity<>(ResponseDto.success(reviewDtos), HttpStatus.OK);
	}

	// 리뷰 작성
	@PostMapping
	public ResponseEntity<ResponseDto> createReview(@PathVariable Long postId, @RequestBody ReviewRequestDto requestDto) {
		if(requestDto.getReviewStar() > 5 || requestDto.getReviewContent().equals("")) { throw new IllegalArgumentException(); }
		ReviewResponseDto reviewDto = reviewService.createReview(postId, requestDto);
		return new ResponseEntity<>(ResponseDto.success(reviewDto), HttpStatus.OK);
	}

	// 리뷰 수정
	@PutMapping("/{reviewId}")
	public ResponseEntity<ResponseDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto) {
		if(requestDto.getReviewStar() > 5 || requestDto.getReviewContent().equals("")) { throw new IllegalArgumentException(); }
		ReviewResponseDto reviewDto = reviewService.updateReview(reviewId, requestDto);
		return new ResponseEntity<>(ResponseDto.success(reviewDto), HttpStatus.OK);
	}

	// 리뷰 삭제
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<ResponseDto> deleteReview(@PathVariable Long reviewId) {
		reviewService.deleteReview(reviewId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}