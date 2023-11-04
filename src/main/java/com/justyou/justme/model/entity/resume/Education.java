package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.EducationDto;
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
@Entity(name = "EDUCATION")
@AuditOverride(forClass = BaseEntity.class)
public class Education extends BaseEntity {
    //학력
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private LocalDate educationStartDt; //입학일자
    private LocalDate educationEndDt; //졸업일자
    private String educationName; //학교명
    private String educationDepartment; //학과 , 계열
    private String educationEtc; //기타 내용

    public static Education of(EducationDto dto){
        return Education.builder()
                .educationStartDt(dto.getEducationStartDt())
                .educationEndDt(dto.getEducationEndDt())
                .educationName(dto.getEducationName())
                .educationDepartment(dto.getEducationDepartment())
                .educationEtc(dto.getEducationEtc())
                .build();
    }

//    public static Education of(EducationDto dto) {
//        Education education = new Education();
//        education.setEducationStartDt(dto.getEducationStartDt());
//        education.setEducationEndDt(dto.getEducationEndDt());
//        education.setEducationName(dto.getEducationName());
//        education.setEducationDepartment(dto.getEducationDepartment());
//        education.setEducationEtc(dto.getEducationEtc());
//        return education;
//    }
}
