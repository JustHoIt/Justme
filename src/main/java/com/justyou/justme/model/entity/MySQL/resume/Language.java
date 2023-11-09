package com.justyou.justme.model.entity.MySQL.resume;

import com.justyou.justme.dto.resume.LanguageDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LANGUAGE")
public class Language {
    //외국어능력시험점수
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String languageName;//시험명
    private String languageScore;//점수
    private LocalDate languageAcquisitionDt;//취득일

    public static Language of(LanguageDto dto, Resume resume){
        return Language.builder()
                .resume(resume)
                .languageName(dto.getLanguageName())
                .languageScore(dto.getLanguageScore())
                .languageAcquisitionDt(dto.getLanguageAcquisitionDt())
                .build();
    }
}
