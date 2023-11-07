package com.justyou.justme.model.repository.Resume;

import com.justyou.justme.model.entity.resume.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    void deleteByResumeId(Long resumeId);
}
