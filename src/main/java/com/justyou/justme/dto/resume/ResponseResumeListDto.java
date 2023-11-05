package com.justyou.justme.dto.resume;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResumeListDto {
    private String title; //이력서 제목
    private LocalDate createDt;
    private LocalDate modifyDt;
    private String Status;
}
