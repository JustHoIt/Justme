package com.justyou.justme.domains.member.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestNewPasswordDto {
    private String UUID;
    private String password;
}
