package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.AwardDto;
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
@Entity(name = "AWARD")
@AuditOverride(forClass = BaseEntity.class)
public class Award extends BaseEntity {
    //수상
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String awardTitle; //상훈명
    private String awardAgency;//수여기관
    private LocalDate awardDt; //수상일자
    private String awardText;//수상내역

    public static Award of(AwardDto dto){
        return Award.builder()
                .awardTitle(dto.getAwardTitle())
                .awardAgency(dto.getAwardAgency())
                .awardDt(dto.getAwardDt())
                .awardText(dto.getAwardText())
                .build();
    }
}
