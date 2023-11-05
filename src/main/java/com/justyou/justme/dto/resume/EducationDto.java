package com.justyou.justme.dto.resume;

import com.justyou.justme.model.entity.resume.Education;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EducationDto {
    private LocalDate educationStartDt; //입학일자
    private LocalDate educationEndDt; //졸업일자
    private String educationName; //학교명
    private String educationDepartment; //학과 , 계열
    private String educationEtc; //기타 내용

    public static EducationDto of(Education education) {
        return EducationDto.builder()
                .educationDepartment(education.getEducationDepartment())
                .educationEndDt(education.getEducationEndDt())
                .educationStartDt(education.getEducationStartDt())
                .educationEtc(education.getEducationEtc())
                .educationName(education.getEducationName())
                .build();
    }
}
