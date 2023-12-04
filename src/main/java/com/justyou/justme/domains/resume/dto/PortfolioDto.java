package com.justyou.justme.domains.resume.dto;

import com.justyou.justme.domains.resume.model.entity.Portfolio;
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

    public static PortfolioDto of(Portfolio portfolio) {
        return PortfolioDto.builder()
                .portfolioTitle(portfolio.getPortfolioTitle())
                .portfolioText(portfolio.getPortfolioText())
                .portfolioStartDt(portfolio.getPortfolioStartDt())
                .portfolioEndDt(portfolio.getPortfolioEndDt())
                .build();
    }
}
