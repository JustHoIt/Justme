package com.justyou.justme.model.entity.MySQL.resume;

import com.justyou.justme.dto.resume.SkillDto;
import lombok.*;

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
