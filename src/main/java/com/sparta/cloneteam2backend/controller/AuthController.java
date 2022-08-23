package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.Auth.TokenDto;
import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.Auth.AuthRequestDto;
import com.sparta.cloneteam2backend.error.RestApiException;
import com.sparta.cloneteam2backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {
    private final AuthService userService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody AuthRequestDto requestDto) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.signup(requestDto)), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.CONFLICT.value());
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.login(requestDto)), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.CONFLICT.value());
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@RequestBody TokenDto requestDto) {
        return new ResponseEntity<>(ResponseDto.success(userService.reissue(requestDto)), HttpStatus.OK);
    }
}