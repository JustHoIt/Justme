package com.justyou.justme.dto;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResumeDto {
    private String email;
    private String name;
    private String message;
    private String responseStatus;
}
