package com.justyou.justme.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;  //PK
    private String email; // 이메일
    private String name; // 이름
    private String password; //비밀번호
    private String phone; //휴대폰번호
    private LocalDate birth; //생일
    private String address; //주소
    private int zipCode; //우편번호
}
