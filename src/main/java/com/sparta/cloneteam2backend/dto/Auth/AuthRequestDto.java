package com.sparta.cloneteam2backend.dto.Auth;

import com.sparta.cloneteam2backend.model.Authority;
import com.sparta.cloneteam2backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {
//    @NotBlank(message = "아이디를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?!.*[^a-zA-z0-9]).{4,12}$",
//            message = "아이디는 4~12 자리이면서 1개 이상의 알파벳, 숫자를 포함해야합니다.")
    private String userUsername;
//    @NotBlank(message = "비밀번호를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?!.*[^a-zA-z0-9]).{4,32}$",
//            message = "비밀번호는 4~32 자리이면서 1개 이상의 알파벳, 숫자를 포함해야합니다.")
    private String userPassword;

    private String userNickname;


    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(userUsername)
                .nickname(userNickname)
                .password(passwordEncoder.encode(userPassword))
                .authority(Authority.ROLE_USER)
                .build();
    }


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userUsername, userPassword);
    }
}