package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CertificationDto {
    private String certificationName;//자격증명
    private String certificationIssuer;//발급기관
    private String certificationScore;//취득점수 및 등급
    private LocalDate certificationAcquisitionDt; // 취득일 LocalDate
}
