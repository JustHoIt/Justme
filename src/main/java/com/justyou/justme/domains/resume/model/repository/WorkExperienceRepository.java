package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    void deleteByResumeId(Long resumeId);
}
