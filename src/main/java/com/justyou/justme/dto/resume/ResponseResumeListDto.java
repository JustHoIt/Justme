package com.justyou.justme.dto.resume;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResumeListDto {
    private String title; //이력서 제목
    private LocalDateTime createdAt; //이력서 만든시간
    private LocalDateTime modifiedAt; //이력서 수정시간
}
