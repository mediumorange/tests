package com.sparta.cloneteam2backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private HttpStatus httpStatus;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, HttpStatus.OK);
    }

    public static <T> ResponseDto<T> fail(T data, HttpStatus httpStatus) {
        return new ResponseDto<>(false, data, httpStatus);
    }
}
