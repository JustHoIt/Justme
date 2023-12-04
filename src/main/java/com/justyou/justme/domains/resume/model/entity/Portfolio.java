package com.justyou.justme.domains.resume.model.entity;

import com.justyou.justme.domains.resume.dto.PortfolioDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PORTFOLIO")
public class Portfolio{
    //외부 링크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String portfolioTitle; //제목
    private LocalDate portfolioStartDt; //시작일
    private LocalDate portfolioEndDt; //종료일
    private String portfolioText; //내용
    //파일(?)

    public static Portfolio of(PortfolioDto dto, Resume resume){
        return Portfolio.builder()
                .resume(resume)
                .portfolioTitle(dto.getPortfolioTitle())
                .portfolioStartDt(dto.getPortfolioStartDt())
                .portfolioEndDt(dto.getPortfolioEndDt())
                .portfolioText(dto.getPortfolioText())
                .build();
    }

}
