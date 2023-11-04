package com.justyou.justme.model.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.justyou.justme.dto.resume.SkillDto;
import com.justyou.justme.model.entity.BaseEntity;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SKILL")
public class Skill{
    //기술
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String skillName; //스킬명

    public static Skill of(SkillDto dto,Resume resume){
        return Skill.builder()
                .resume(resume)
                .skillName(dto.getSkillName())
                .build();
    }
}
