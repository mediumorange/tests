package com.sparta.cloneteam2backend.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApiException {
    private int code;
    private String errorMessage;
}
