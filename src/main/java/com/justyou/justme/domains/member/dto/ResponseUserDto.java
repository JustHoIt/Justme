package com.justyou.justme.domains.member.dto;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {
    private String email;
    private String name;
    private String message;
    private String responseStatus;
}
