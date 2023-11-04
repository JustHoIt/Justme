package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.LanguageDto;
import com.justyou.justme.model.entity.BaseEntity;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LANGUAGE")
@AuditOverride(forClass = BaseEntity.class)
public class Language extends BaseEntity {
    //외국어능력시험점수
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String languageName;//시험명
    private String languageScore;//점수
    private LocalDate languageAcquisitionDt;//취득일

    public static Language of(LanguageDto dto){
        return Language.builder()
                .languageName(dto.getLanguageName())
                .languageScore(dto.getLanguageScore())
                .languageAcquisitionDt(dto.getLanguageAcquisitionDt())
                .build();
    }
}
