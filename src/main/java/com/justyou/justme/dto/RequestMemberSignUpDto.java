package com.justyou.justme.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RequestMemberSignUpDto {
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Column(unique = true)
    private String email; // 이메일
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name; // 이름
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password; //비밀번호
    @Column(unique = true)
    @NotBlank(message = "휴대폰 번호는 필수 입력 값 입니다.")
    private String phone; //휴대폰번호
    @NotNull(message = "생년월일은 필수 입력 값 입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "생년월인은 오늘 날짜가 불가능합니다.")
    private LocalDate birth;


    private String address; //주소
    private int zipCode; //우편번호
}
