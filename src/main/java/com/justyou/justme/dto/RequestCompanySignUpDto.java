package com.justyou.justme.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class RequestCompanySignUpDto {
    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String companyName; //회사명
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Column(unique = true)
    private String email; //회사 HR 이메일
    @Column(unique = true)
    @NotBlank(message = "회사 번호는 필수 입력 값 입니다.")
    private String officeTel; // 회사 번호
    @Column(unique = true)
    @NotBlank(message = "휴대폰 번호는 필수 입력 값 입니다.")
    private String phone; //인사 담당자 번호
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password; //비밀번호
}
