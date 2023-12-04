package com.justyou.justme.domains.resume.model.entity;

import com.justyou.justme.domains.resume.dto.AwardDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AWARD")
public class Award  {
    //수상
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    private String awardTitle; //상훈명
    private String awardAgency;//수여기관
    private LocalDate awardDt; //수상일자
    private String awardText;//수상내역

    public static Award of(AwardDto dto, Resume resume){
        return Award.builder()
                .resume(resume)
                .awardTitle(dto.getAwardTitle())
                .awardAgency(dto.getAwardAgency())
                .awardDt(dto.getAwardDt())
                .awardText(dto.getAwardText())
                .build();
    }
}
