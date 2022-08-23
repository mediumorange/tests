package com.sparta.cloneteam2backend.dto.user;

import com.sparta.cloneteam2backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String userUsername;
    private String userNickname;


    public static AuthResponseDto of(User user) {
        return new AuthResponseDto(user.getUserUsername(), user.getUserNickname());
    }
}
