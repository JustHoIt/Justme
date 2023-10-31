package com.justyou.justme.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, ""),
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 주소 입니다."),
    ALREADY_REGISTERED_PHONE(HttpStatus.BAD_REQUEST, "이미 가입된 휴대폰 번호 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
