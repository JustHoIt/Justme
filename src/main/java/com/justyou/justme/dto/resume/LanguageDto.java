package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LanguageDto {
    private String languageName;//시험명
    private String languageScore;//점수
    private LocalDate languageAcquisitionDt;//취득일
}
