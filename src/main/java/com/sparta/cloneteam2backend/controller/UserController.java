package com.sparta.cloneteam2backend.controller;

import com.sparta.cloneteam2backend.dto.user.TokenDto;
import com.sparta.cloneteam2backend.dto.ResponseDto;
import com.sparta.cloneteam2backend.dto.user.AuthRequestDto;
import com.sparta.cloneteam2backend.error.RestApiException;
import com.sparta.cloneteam2backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody AuthRequestDto requestDto) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.signup(requestDto)), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.CONFLICT.value());
            restApiException.setMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.login(requestDto)), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.NOT_FOUND.value());
            restApiException.setMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@RequestBody TokenDto requestDto) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.reissue(requestDto)), HttpStatus.OK);
        } catch (RuntimeException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.CONFLICT.value());
            restApiException.setMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/me")
    public ResponseEntity<ResponseDto> check() {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.getMyInfo()), HttpStatus.OK);
        } catch (RuntimeException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.NOT_FOUND.value());
            restApiException.setMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("{userUsername}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable String userUsername) {
        try {
            return new ResponseEntity<>(ResponseDto.success(userService.getUserInfo(userUsername)), HttpStatus.OK);
        } catch (RuntimeException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setCode(HttpStatus.NOT_FOUND.value());
            restApiException.setMessage(ex.getMessage());
            return new ResponseEntity<>(ResponseDto.fail(restApiException, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}