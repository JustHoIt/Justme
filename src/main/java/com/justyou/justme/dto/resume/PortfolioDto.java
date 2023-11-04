package com.justyou.justme.dto.resume;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PortfolioDto {
    private String portfolioTitle; //제목
    private LocalDate portfolioStartDt; //시작일
    private LocalDate portfolioEndDt; //종료일
    private String portfolioText; //내용
}
