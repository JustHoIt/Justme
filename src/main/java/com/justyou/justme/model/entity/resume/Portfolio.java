package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.PortfolioDto;
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
