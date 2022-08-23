package com.sparta.cloneteam2backend.dto.post;

import com.sparta.cloneteam2backend.model.Category;
import com.sparta.cloneteam2backend.model.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequestDto {

    private String postTitle;
    private String postAddress;
    private String postContent;

    @Enumerated(EnumType.STRING)
    private Category postCategory;

    private String postFee;

    private ArrayList facilitiesList = new ArrayList();

    public Post createPost() {
        return Post.builder()
                .postTitle(postTitle)
                .postAddress(postAddress)
                .postContent(postContent)
                .postCategory(postCategory)
                .postFee(postFee)
                .build();
    }

}
