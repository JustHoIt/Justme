package com.justyou.justme.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, ""),
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 주소 입니다."),
    ALREADY_REGISTERED_PHONE(HttpStatus.BAD_REQUEST, "이미 가입된 휴대폰 번호 입니다."),
    NOT_MATCH_USER(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."),
    NOT_EMAIL_AUTHENTICATE_USER(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않은 유저입니다."),
    WITHDRAW_USER(HttpStatus.BAD_REQUEST, "회원탈퇴한 유저입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
