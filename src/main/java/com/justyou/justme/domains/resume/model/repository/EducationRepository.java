package com.justyou.justme.domains.resume.model.repository;


import com.justyou.justme.domains.resume.model.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    void deleteByResumeId(Long resumeId);
}
