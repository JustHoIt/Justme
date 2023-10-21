package com.justyou.justme.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INITIAL_SETTINGS(HttpStatus.BAD_REQUEST,"초기설정");


    private final HttpStatus httpStatus;
    private final String message;

}
