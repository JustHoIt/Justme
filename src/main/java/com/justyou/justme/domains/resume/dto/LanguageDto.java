package com.justyou.justme.domains.resume.dto;

import com.justyou.justme.domains.resume.model.entity.Language;
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

    public static LanguageDto of(Language language) {
        return LanguageDto.builder()
                .languageAcquisitionDt(language.getLanguageAcquisitionDt())
                .languageName(language.getLanguageName())
                .languageScore(language.getLanguageScore())
                .build();
    }
}
