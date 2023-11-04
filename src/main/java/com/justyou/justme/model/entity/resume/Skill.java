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
@AuditOverride(forClass = BaseEntity.class)
public class Skill extends BaseEntity {
    //기술
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String skillName; //스킬명

    public static Skill of(SkillDto dto){
        return Skill.builder()
                .skillName(dto.getSkillName())
                .build();
    }
}
