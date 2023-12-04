package com.justyou.justme.domains.resume.model.entity;

import com.justyou.justme.domains.resume.dto.LinkDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LINK")
public class Link  {
    //외부 링크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String linkName; // 링크 명(ex. Google Drive)
    private String linkUrl; //링크 주소


    public static Link of(LinkDto dto, Resume resume){
        return Link.builder()
                .resume(resume)
                .linkName(dto.getLinkName())
                .linkUrl(dto.getLinkUrl())
                .build();
    }
}
