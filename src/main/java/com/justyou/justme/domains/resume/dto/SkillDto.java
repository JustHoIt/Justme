package com.justyou.justme.domains.resume.dto;

import com.justyou.justme.domains.resume.model.entity.Skill;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {
    private String skillName; //스킬명

    public static SkillDto of(Skill skill) {
        return SkillDto.builder()
                .skillName(skill.getSkillName())
                .build();
    }
}
