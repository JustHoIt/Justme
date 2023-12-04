package com.justyou.justme.domains.resume.model.entity;

import com.justyou.justme.domains.resume.dto.CertificationDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CERTIFICATION")
public class Certification {
    //자격증 Certification
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String certificationName;//자격증명
    private String certificationIssuer;//발급기관
    private String certificationScore;//취득점수 및 등급
    private LocalDate certificationAcquisitionDt; // 취득일 LocalDate

    public static Certification of(CertificationDto dto, Resume resume){
        return Certification.builder()
                .resume(resume)
                .certificationName(dto.getCertificationName())
                .certificationIssuer(dto.getCertificationIssuer())
                .certificationScore(dto.getCertificationScore())
                .certificationAcquisitionDt(dto.getCertificationAcquisitionDt())
                .build();
    }
}
