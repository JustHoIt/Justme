package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class WorkExperienceDto {
    private String workExperienceCompanyName; //회사명
    private String workExperienceDepartment; //부서
    private String workExperiencePosition; //역할(포지션)
    private String workExperienceTitle; //제목
    private String workExperienceText; //내용
    private LocalDate workExperienceStartDt; //시작일
    private LocalDate workExperienceEndDt; //종료일
}
