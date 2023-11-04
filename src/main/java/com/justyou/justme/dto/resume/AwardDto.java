package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AwardDto {
    private String awardTitle; //상훈명
    private String awardAgency;//수여기관
    private LocalDate awardDt; //수상일자
    private String awardText;//수상내역
}
