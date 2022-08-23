package com.sparta.cloneteam2backend.service;

import com.sparta.cloneteam2backend.dto.user.TokenDto;
import com.sparta.cloneteam2backend.dto.user.AuthRequestDto;
import com.sparta.cloneteam2backend.dto.user.AuthResponseDto;
import com.sparta.cloneteam2backend.jwt.TokenProvider;
import com.sparta.cloneteam2backend.model.RefreshToken;
import com.sparta.cloneteam2backend.model.User;
import com.sparta.cloneteam2backend.repository.RefreshTokenRepository;
import com.sparta.cloneteam2backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AuthResponseDto signup(AuthRequestDto requestDto) {
        if (userRepository.existsByUserUsername(requestDto.getUserUsername())) {
            throw new IllegalArgumentException("이미 가입되어 있는 유저입니다");
        }

        User user = requestDto.toUser(passwordEncoder);
        return AuthResponseDto.of(userRepository.save(user));
    }


    public TokenDto login(AuthRequestDto requestDto) {

        // ID 중복
        if (!userRepository.existsByUserUsername(requestDto.getUserUsername())) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다");
        }

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }


    @Transactional
    public TokenDto reissue(TokenDto requestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(requestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 User ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(requestDto.getAccessToken());

        // 3. 저장소에서 User ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(requestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }


    @Transactional
    public User getUserInfo(String userUsername) {
        return userRepository.findByUserUsername(userUsername)
                .orElseThrow(
                        () -> new RuntimeException("유저 정보가 없습니다")
                );
    }


    //현재 SecurityContext에 있는 유저 정보 가져오기기
    @Transactional
    public User getMyInfo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다");
        }

        return userRepository.findByUserUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }
}