package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.post.PostRequestDto;
import com.sparta.cloneteam2backend.dto.post.PostResponseDto;
import com.sparta.cloneteam2backend.model.Img;
import com.sparta.cloneteam2backend.model.Imgtarget;
import com.sparta.cloneteam2backend.model.Post;
import com.sparta.cloneteam2backend.repository.ImgRepository;
import com.sparta.cloneteam2backend.repository.PostRepository;
import com.sparta.cloneteam2backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final ImgRepository imgRepository;

    // 포스트 리스트 조회
    public List<PostResponseDto> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            Long postId = post.getPostId();
            Double reviewStar = reviewRepository.existsAllReviewStar(postId).orElse(0.0d);
            List<Img> imageFiles = imgRepository.findAllByTargetId(Imgtarget.POST, postId);
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .post(post)
                    .reviewStar(reviewStar)
                    .imageFiles(imageFiles)
                    .build();
            postList.add(postResponseDto);
        }
        return postList;
    }

    // 포스트 상세 조회
    public PostResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        Double reviewStar = reviewRepository.existsAllReviewStar(postId).orElse(0.0d);
        List<Img> imageFiles = imgRepository.findAllByTargetId(Imgtarget.POST, postId);
        return PostResponseDto.builder()
                .post(post)
                .reviewStar(reviewStar)
                .imageFiles(imageFiles)
                .build();
    }

    // 포스트 생성
    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = requestDto.createPost();
        postRepository.save(post);
        return post;
    }

    // 포스트 수정
    @Transactional
    public Post updatePost(Long postId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        post.update(requestDto);
        return post;
    }

    // 포스트 삭제
    @Transactional
    public Long deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        postRepository.deleteById(postId);
        return postId;
    }

    // 포스트 객체 가져오기
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
    }
}
