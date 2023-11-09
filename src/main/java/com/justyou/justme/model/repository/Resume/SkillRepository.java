package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.MySQL.resume.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    void deleteByResumeId(Long resumeId);
}
