package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.CertificationDto;
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
@Entity(name = "CERTIFICATION")
@AuditOverride(forClass = BaseEntity.class)
public class Certification extends BaseEntity {
    //자격증 Certification
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String certificationName;//자격증명
    private String certificationIssuer;//발급기관
    private String certificationScore;//취득점수 및 등급
    private LocalDate certificationAcquisitionDt; // 취득일 LocalDate

    public static Certification of(CertificationDto dto){
        return Certification.builder()
                .certificationName(dto.getCertificationName())
                .certificationIssuer(dto.getCertificationIssuer())
                .certificationScore(dto.getCertificationScore())
                .certificationAcquisitionDt(dto.getCertificationAcquisitionDt())
                .build();
    }
}
