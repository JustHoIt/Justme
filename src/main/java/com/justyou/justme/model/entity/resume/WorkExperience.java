package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.WorkExperienceDto;
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
@Entity(name = "WORKEXPERIENCE")
@AuditOverride(forClass = BaseEntity.class)
public class WorkExperience extends BaseEntity {
    //외부 링크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String workExperienceCompanyName; //회사명
    private String workExperienceDepartment; //부서
    private String workExperiencePosition; //역할(포지션)
    private String workExperienceTitle; //제목
    private String workExperienceText; //내용
    private LocalDate workExperienceStartDt; //시작일
    private LocalDate workExperienceEndDt; //종료일

    public static WorkExperience of(WorkExperienceDto dto){
        return WorkExperience.builder()
                .workExperienceCompanyName(dto.getWorkExperienceCompanyName())
                .workExperienceDepartment(dto.getWorkExperienceDepartment())
                .workExperiencePosition(dto.getWorkExperiencePosition())
                .workExperienceTitle(dto.getWorkExperienceTitle())
                .workExperienceText(dto.getWorkExperienceText())
                .workExperienceStartDt(dto.getWorkExperienceStartDt())
                .workExperienceEndDt(dto.getWorkExperienceEndDt())
                .build();
    }

}
