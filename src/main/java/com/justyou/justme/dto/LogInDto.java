package com.justyou.justme.dto;


import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogInDto {
    private String email;
    private String password;
}
