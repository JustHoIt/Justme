package com.justyou.justme.dto.resume;


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
