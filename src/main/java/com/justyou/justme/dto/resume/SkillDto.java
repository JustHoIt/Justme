package com.justyou.justme.dto.resume;

import com.justyou.justme.model.entity.MySQL.resume.Skill;
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
