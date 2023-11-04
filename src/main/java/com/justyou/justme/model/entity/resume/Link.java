package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.LinkDto;
import com.justyou.justme.model.entity.BaseEntity;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LINK")
@AuditOverride(forClass = BaseEntity.class)
public class Link extends BaseEntity {
    //외부 링크
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String linkName; // 링크 명(ex. Google Drive)
    private String linkUrl; //링크 주소


    public static Link of(LinkDto dto){
        return Link.builder()
                .linkName(dto.getLinkName())
                .linkUrl(dto.getLinkUrl())
                .build();
    }
}
