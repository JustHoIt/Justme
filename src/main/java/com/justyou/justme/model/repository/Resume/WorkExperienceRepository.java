package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.MySQL.resume.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    void deleteByResumeId(Long resumeId);
}
